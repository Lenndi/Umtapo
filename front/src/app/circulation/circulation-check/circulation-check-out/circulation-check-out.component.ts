import {Component, ViewChild} from '@angular/core';
import {ItemService} from '../../../../service/item.service';
import {CirculationDataService} from '../../../../service/data-binding/circulation-data.service';
import {Borrower} from '../../../../entity/borrower';
import {Observable} from 'rxjs';
import {Item} from '../../../../entity/item';
import {ModalDirective, TypeaheadMatch} from 'ngx-bootstrap';
import {Loan} from '../../../../entity/loan';
import {LoanService} from '../../../../service/loan.service';
import {ToastrService} from 'ngx-toastr';
import {ItemFilter} from '../../../../service/filter/item-filter';
import {Pageable} from '../../../../util/pageable';
import {logger} from '../../../../environments/environment';

@Component({
  selector: 'umt-circulation-check-out',
  templateUrl: './circulation-check-out.component.html',
  styleUrls: ['./circulation-check-out.component.scss'],
  providers: [ItemService]

})
export class CirculationCheckOutComponent {
  @ViewChild('childModal') public childModal: ModalDirective;
  itemsSerialNumber: Item[] = [];
  itemsTitle: Item[] = [];
  items: Item[] = [];
  selectedItem: Item;
  page: number = 0;
  size: number = 10;
  internalId: number;
  serialNumber: string;
  title: string;
  overQuota: boolean = false;
  public dataSourceSerialNumber: Observable<Item[]>;
  public dataSourceTitle: Observable<Item[]>;

  constructor(private itemService: ItemService,
              private loanService: LoanService,
              public dataService: CirculationDataService,
              public toastr: ToastrService) {

    this.selectedItem = new Item();

    this.dataSourceSerialNumber = Observable
      .create((observer: any) => {
        // Runs on every search
        observer.next(this.serialNumber);
      })
      .switchMap(serialNumber => {
          let filter: ItemFilter = new ItemFilter();
          filter.serialType = 'ISBN';
          filter.serialNumber = serialNumber;

          this.itemService.findWithFilters(this.getPageable(), filter);
        })
      .catch(err => logger.error(err))
      .map(res => this.itemsSerialNumber = res.content as Item[]);

    this.dataSourceTitle = Observable
      .create((observer: any) => {
        observer.next(this.title);
      })
      .mergeMap(mainTitle => this.getTitlesAsObservable(mainTitle));
  }

  public typeaheadOnSelectSerialNumber(itemTypeahead: TypeaheadMatch): void {
    this.selectedItem = itemTypeahead.item;
  }

  public typeaheadOnSelectTitle(itemTypeahead: TypeaheadMatch): void {
    this.selectedItem = itemTypeahead.item;
  }

  public changeTypeaheadNoResultsTitle(e: boolean): void {
    if (!this.title) {
      this.itemsTitle = [];
    }
  }

  public changeTypeaheadNoResultsSerialNumber(e: boolean): void {
    if (!this.serialNumber) {
      this.itemsSerialNumber = [];
    }
  }

  public showChildModal(): void {
    this.childModal.show();
  }

  public hideChildModal(): void {
    this.childModal.hide();
  }

  borrowOverQuota() {
    this.overQuota = true;
    this.hideChildModal();
    this.checkout();
  }

  checkout() {
    if (this.dataService.borrower.loans != null && this.dataService.borrower.loans.length + 1 >
      this.dataService.borrower.quota && this.overQuota === false) {
      this.showChildModal();
    } else {
      this.overQuota = false;
      let loan: Loan = new Loan();
      let item: Item = new Item();

      loan.item = new Item();
      loan.borrower = new Borrower();
      loan.returned = false;
      loan.borrower.id = this.dataService.borrower.id;

      if (this.itemsSerialNumber) {
        this.items = this.itemsSerialNumber;
      } else if (this.title) {
        this.items = this.itemsTitle;
      }
      if (this.internalId) {
        let observable = this.itemService.searchItemByInternalId(this.internalId)
          .catch(err => {
            this.toastr.error(`Un problème est survenu le document ne peut être emprunté`, 'Problème');
            return Observable.throw(err); // observable needs to be returned or exception raised
          })
          .map(res => {
            if (res.status == 204) {
              this.toastr.warning(`Aucun document ne comporte cet identifiant`, 'Non trouvé');
              observable.unsubscribe();
            }
            item = res.json();
            loan.item.id = item.id;
          })
          .subscribe(() => this.createLoan(loan));
      } else if (this.selectedItem) {
        item = this.selectedItem;
        loan.item.id = item.id;
        this.createLoan(loan);
      } else if (this.items) {
        if (this.items.length == 0) {
          if (this.serialNumber) {
            this.toastr.warning(`Aucun document n'est lié à cette information`, 'Pas de document!');
          } else {
            this.toastr.warning('Vous devez entrer une information pour emprunter un livre.', 'Champs vides!');
          }
        } else if (this.items.length > 1) {
          this.toastr.warning('Plusieurs documents ont ce numéro de série, vous devez en sélectionner un.',
            'Plusieurs documents existants!');
        } else if (this.items.length == 1) {

        }
      }
    }
  }

  private getTitlesAsObservable(mainTitle: string): Observable<Item[]> {
    let filter: ItemFilter = new ItemFilter();
    filter.mainTitle = mainTitle;

    return this.itemService.findWithFilters(this.getPageable(), filter)
      .map(response => response.content);
  }

  private createLoan(loan: Loan): void {
    this.loanService.createLoan(loan)
      .catch(err => {
        this.toastr.warning(`Le document a déjà été emprunté`, 'Problème');
        return Observable.throw(err); // observable needs to be returned or exception raised
      })
      .subscribe(response => {
        this.loanService.find(response.json().id).then(succes => {
          if (!this.dataService.borrower.loans) {
            this.dataService.borrower.loans = [];
          }
          this.dataService.borrower.loans.push(succes);
        });
        this.toastr.success(`Le document a bien été emprunté`, 'Emprunt réussi');
      });
  }

  private getPageable(): Pageable {
    let pageable: Pageable = new Pageable('mainTitle');
    pageable.size = this.size;
    pageable.page = this.page;

    return pageable;
  }
}
