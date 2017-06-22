import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {PriceValidator} from '../../../../../validator/price.validator';
import {LibraryService} from '../../../../../service/library.service';
import {Library} from '../../../../../entity/library';
import {Item} from '../../../../../entity/item';
import {ItemRegistrationDataService} from '../../../../../service/data-binding/item-registration-data.service';
import {ItemService} from '../../../../../service/item.service';
import {logger} from '../../../../../environments/environment';
import {ShelfMark} from '../../../../../entity/shelfmark';
import {CustomMap} from '../../../../../enumeration/custom-map';
import {conditionEnum} from '../../../../../enumeration/fr';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {ExternalLibraryModalComponent} from '../../../various/external-library-modal/external-library-modal.component';
import {VariousItemDataService} from '../../../../../service/data-binding/various-item-data.service';
import {ModalDirective} from 'ngx-bootstrap';
import {NewItemDataService} from '../../../../../service/new-item-data.service';

@Component({
  selector: 'app-internal-informations',
  templateUrl: './internal-informations.component.html',
  styleUrls: ['./internal-informations.component.scss'],
  providers: [ItemService, VariousItemDataService]
})
export class InternalInformationsComponent implements OnInit {
  @ViewChild(ExternalLibraryModalComponent)
  private externalLibraryModalComponent: ExternalLibraryModalComponent;

  @ViewChild('confirmationModal')
  public confirmationModal: ModalDirective;

  itemForm: FormGroup;
  internalId: FormControl;
  shelfmark1: FormControl;
  shelfmark2: FormControl;
  shelfmark3: FormControl;
  shelfmark4: FormControl;
  purchasePrice: FormControl;
  condition: FormControl;
  loanable: FormControl;
  externalLibrary: FormControl;
  localLibrary: Library;
  hasCustomId: boolean;
  isExternalItem: boolean;
  conditionEnum: CustomMap;

  constructor(
    private formBuilder: FormBuilder,
    private libraryService: LibraryService,
    public dataService: ItemRegistrationDataService,
    public dataServiceItem: NewItemDataService,
    public variousItemDataService: VariousItemDataService,
    private itemService: ItemService,
    private router: Router,
    public toastr: ToastrService
  ) {
    this.localLibrary = this.libraryService.findLocally();
    this.internalId = new FormControl('');
    this.shelfmark1 = new FormControl('', Validators.required);
    let shelfmarkNb: number = this.localLibrary.shelfMarkNb;
    if (shelfmarkNb > 1) {
      this.shelfmark2 = new FormControl('', Validators.required);
    } else {
      this.shelfmark2 = new FormControl('');
    }
    if (shelfmarkNb > 2) {
      this.shelfmark3 = new FormControl('', Validators.required);
    } else {
      this.shelfmark3 = new FormControl('');
    }
    if (shelfmarkNb > 3) {
      this.shelfmark4 = new FormControl('', Validators.required);
    } else {
      this.shelfmark4 = new FormControl('');
    }
    this.purchasePrice = new FormControl('', [Validators.required, PriceValidator.isAPrice]);
    this.condition = new FormControl('', Validators.required);
    this.loanable = new FormControl(true);
    this.externalLibrary = new FormControl('');
    this.hasCustomId = false;
    this.isExternalItem = false;
    this.conditionEnum = conditionEnum;
  }

  ngOnInit() {
    this.itemForm = this.formBuilder.group({
      'internalId': this.internalId,
      'shelfmark1': this.shelfmark1,
      'shelfmark2': this.shelfmark2,
      'shelfmark3': this.shelfmark3,
      'shelfmark4': this.shelfmark4,
      'purchasePrice': this.purchasePrice,
      'condition': this.condition,
      'loanable': this.loanable,
      'externalLibrary': this.externalLibrary
    });
  }

  onItemSubmit(): void {
    if (this.itemForm.valid) {
      let value = this.itemForm.value;
      let item: Item = new Item();

      if (value.internalId != '') {
        item.internalId = value.internalId;
      }
      item.shelfmark = new ShelfMark();
      item.shelfmark.field1 = value.shelfmark1;
      item.shelfmark.field2 = value.shelfmark2;
      item.shelfmark.field3 = value.shelfmark3;
      item.shelfmark.field4 = value.shelfmark4;
      item.purchasePrice = value.purchasePrice.toString().replace(',', '.');
      item.loanable = value.loanable;
      item.condition = value.condition;
      item.currency = this.localLibrary.currency;
      item.library = this.localLibrary;
      item.record = this.dataService.record;
      if (value.externalLibrary != -1) {
        item.externalLibrary = new Library();
        item.externalLibrary.id = value.externalLibrary;
      } else {
        item.externalLibrary = null;
      }

      this.itemService.save(item)
        .then(item => {
          this.dataService.item = item;
          this.dataServiceItem.item = item;
          this.variousItemDataService.item = item;
          this.confirmationModal.show();
        })
        .catch(error => logger.error(error));
    } else {
      logger.info('Invalid form :', this.itemForm);

      if (this.itemForm.controls['purchasePrice'].invalid) {
        this.toastr.error(`Le prix d'achat indiqué n'est pas conforme`, 'Oops');
        logger.warn('Bad purchasePrice', this.itemForm.value.purchasePrice);
      } else if (this.itemForm.controls['condition'].invalid) {
        this.toastr.error(`Veuillez indiquer l'état du document`, 'Oops');
        logger.warn('Bad condition', this.itemForm.value.condition);
      } else if (this.itemForm.controls['shelfmark1'].invalid || this.itemForm.controls['shelfmark2'].invalid
          || this.itemForm.controls['shelfmark3'].invalid || this.itemForm.controls['shelfmark4'].invalid) {
        this.toastr.error(`Le format de cotation est incorrect`, 'Oops');
        logger.warn('Bad shelfmark');
      } else {
        this.toastr.error(`Impossible d'enregistrer le document`, 'Oops');
      }
    }
  }

  newExternalLibrary(): void {
    this.externalLibraryModalComponent.showModal();
  }

  switchCustomId(): void {
    this.hasCustomId = !this.hasCustomId;
    this.internalId.setValue('');
  }

  switchExternalLibrary(): void {
    this.isExternalItem = !this.isExternalItem;
    if (this.isExternalItem) {
      this.externalLibraryModalComponent.populateExternalLibraries();
    } else {
      this.itemForm.value.externalLibrary = -1;
    }
  }

  formatCotation(): string {
    let shelfmark: ShelfMark = this.dataService.item.shelfmark;
    let formattedShelfmark = '';

    formattedShelfmark += shelfmark.field1;
    if (this.localLibrary.shelfMarkNb > 1) {
      formattedShelfmark += '|' + shelfmark.field2;
    }
    if (this.localLibrary.shelfMarkNb > 2) {
      formattedShelfmark += '|' + shelfmark.field3;
    }
    if (this.localLibrary.shelfMarkNb > 3) {
      formattedShelfmark += '|' + shelfmark.field4;
    }

    return formattedShelfmark;
  }

  toSearchView(): void {
    this.router.navigate(['cataloging/registration/search']);
  }
}
