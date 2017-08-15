import {Component, OnDestroy, ViewChild} from '@angular/core';
import {ModalDirective} from 'ngx-bootstrap';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs/Subscription';
import {Item} from '../../../../entity/item';
import {ItemService} from '../../../../service/item.service';
import {ItemDatatableDataService} from '../../../../service/data-binding/item-datatable-data.service';
import {ToastrService} from 'ngx-toastr';
import {logger} from '../../../../environments/environment';
import {Library} from '../../../../entity/library';
import {CustomMap} from '../../../../enumeration/custom-map';
import {conditionEnum} from '../../../../enumeration/fr';
import {LibraryService} from '../../../../service/library.service';
import {PriceValidator} from '../../../../validator/price.validator';
import {ExternalLibraryModalComponent} from '../../various/external-library-modal/external-library-modal.component';
import {VariousItemDataService} from '../../../../service/data-binding/various-item-data.service';

@Component({
  selector: 'umt-item-edit',
  templateUrl: './item-edit.component.html',
  styleUrls: ['./item-edit.component.scss'],
  providers: [VariousItemDataService]
})
export class ItemEditComponent implements OnDestroy {

  @ViewChild('itemEditModal')
  public itemEditModal: ModalDirective;

  @ViewChild(ExternalLibraryModalComponent)
  public externalLibraryModalComponent: ExternalLibraryModalComponent;

  itemForm: FormGroup;
  shelfmark1: FormControl;
  shelfmark2: FormControl;
  shelfmark3: FormControl;
  shelfmark4: FormControl;
  purchasePrice: FormControl;
  condition: FormControl;
  loanable: FormControl;
  externalLibrary: FormControl;
  externalLibraryForm: FormGroup;
  externalLibraryName: FormControl;
  localLibrary: Library;
  hasCustomId: boolean;
  isExternalItem: boolean;
  conditionEnum: CustomMap;
  itemSubscription: Subscription;
  selectedItem: Item;

  constructor(
    private formBuilder: FormBuilder,
    private itemService: ItemService,
    private libraryService: LibraryService,
    public dataService: ItemDatatableDataService,
    public variousItemDataService: VariousItemDataService,
    public toastr: ToastrService
  ) {
    this.itemSubscription = this.dataService.selectedItem$.subscribe(
      item => {
        this.selectedItem = item;
        if (item.externalLibrary != null && item.externalLibrary.id != null) {
          this.isExternalItem = true;
        }
        this.populateForm();
      }
    );
    this.localLibrary = this.libraryService.findLocally();
    this.isExternalItem = false;
    this.conditionEnum = conditionEnum;
    this.populateExternalLibraries();
  }

  onUpdate(value: any): void {
    if (this.itemForm.valid) {
      logger.info('valid form :', value);
      this.saveData(value);

      this.itemService
        .update(this.selectedItem)
        .then(item => {
          this.hideModal();
          this.dataService.notifyUpdatedItem(item);
          this.toastr.info(`L'item a été mis à jour`, 'OK');
        })
        .catch(response => {
          logger.error(response);
          this.hideModal();
          this.toastr.error(`Problème durant la mise à jour de l'item`, 'Problème');
        });
    } else {
      logger.info('Invalid form :', this.itemForm);
      this.toastr.error(`Le formulaire n'est pas valide`, 'Erreur');
    }
  }

  public hideModal(): void {
    this.itemEditModal.hide();
  }

  public onHidden(): void {
    this.dataService.isEditShown = false;
  }

  newExternalLibrary(): void {
    this.externalLibraryModalComponent.showModal();
  }

  switchExternalLibrary(): void {
    this.isExternalItem = !this.isExternalItem;

    if (!this.isExternalItem) {
      this.itemForm.get('externalLibrary').setValue(-1);
    }
  }

  private populateForm(): void {
    let shelfmarkNb: number = this.localLibrary.shelfMarkNb;
    this.shelfmark1 = new FormControl(this.selectedItem.shelfmark.field1, Validators.required);
    if (shelfmarkNb > 1) {
      this.shelfmark2 = new FormControl(this.selectedItem.shelfmark.field2, Validators.required);
    } else {
      this.shelfmark2 = new FormControl('');
    }
    if (shelfmarkNb > 2) {
      this.shelfmark3 = new FormControl(this.selectedItem.shelfmark.field3, Validators.required);
    } else {
      this.shelfmark3 = new FormControl('');
    }
    if (shelfmarkNb > 3) {
      this.shelfmark4 = new FormControl(this.selectedItem.shelfmark.field4, Validators.required);
    } else {
      this.shelfmark4 = new FormControl('');
    }
    this.purchasePrice =
      new FormControl(this.selectedItem.purchasePrice, [Validators.required, PriceValidator.isAPrice]);
    this.condition = new FormControl(this.selectedItem.condition, Validators.required);
    this.loanable = new FormControl(this.selectedItem.loanable, Validators.required);
    this.externalLibrary =
        new FormControl(this.selectedItem.externalLibrary ? this.selectedItem.externalLibrary.id : '');
    this.externalLibraryName = new FormControl('');

    if (!this.itemForm) {
      this.itemForm = this.formBuilder.group({
        'shelfmark1': this.shelfmark1,
        'shelfmark2': this.shelfmark2,
        'shelfmark3': this.shelfmark3,
        'shelfmark4': this.shelfmark4,
        'purchasePrice': this.purchasePrice,
        'condition': this.condition,
        'loanable': this.loanable,
        'externalLibrary': this.externalLibrary
      });
      this.externalLibraryForm = this.formBuilder.group({
        'externalLibraryName': this.externalLibraryName
      });
    }
  }

  private saveData(value: any) {
    this.selectedItem.shelfmark.field1 = value.shelfmark1;
    this.selectedItem.shelfmark.field2 = value.shelfmark2;
    this.selectedItem.shelfmark.field3 = value.shelfmark3;
    this.selectedItem.shelfmark.field4 = value.shelfmark4;
    this.selectedItem.purchasePrice = value.purchasePrice.toString().replace(',', '.');
    this.selectedItem.condition = value.condition;
    this.selectedItem.loanable = value.loanable;
    this.selectedItem.externalLibrary = value.externalLibrary;
    if (value.externalLibrary != -1) {
      this.selectedItem.externalLibrary = new Library();
      this.selectedItem.externalLibrary.id = value.externalLibrary;
    } else {
      this.selectedItem.externalLibrary = null;
    }
  }

  public populateExternalLibraries(): void {
    this.libraryService.findExternalLibraries()
      .then(externalLibraries => this.variousItemDataService.externalLibraries = externalLibraries)
      .catch(error => logger.error(error));
  }

  ngOnDestroy() {
    this.itemSubscription.unsubscribe();
  }
}
