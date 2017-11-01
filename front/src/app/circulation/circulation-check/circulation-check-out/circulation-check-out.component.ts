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

@Component({
  selector: 'umt-circulation-check-out',
  templateUrl: './circulation-check-out.component.html',
  styleUrls: ['./circulation-check-out.component.scss'],
  providers: [ItemService]

})
export class CirculationCheckOutComponent {
  @ViewChild('childModal') public childModal: ModalDirective;
  items: Item[] = [];
  selectedItem: Item = null;
  page: number = 0;
  size: number = 10;
  internalId: number;
  serialNumber: string;
  title: string;
  overQuota: boolean = false;
  isItemLoading: boolean = false;
  isExecutingLoan: boolean = false;
  dataSource: Observable<Item[]>;
  lastFocus: string = '';

  constructor(private itemService: ItemService,
              private loanService: LoanService,
              public dataService: CirculationDataService,
              public toastr: ToastrService) {

    this.dataSource = Observable
      .create((observer: any) => {
        if (this.internalId) { observer.next(this.internalId); }
        if (this.serialNumber) { observer.next(this.serialNumber); }
        if (this.title) { observer.next(this.title); }
      })
      .distinctUntilChanged()
      .switchMap(input => {
        if (this.internalId) {
          return this.getItemsByInternalId(input);
        } else if (this.serialNumber) {
          return this.getItemsBySerialNumber(input);
        } else if (this.title) {
          return this.getItemsByTitle(input);
        }
      })
      .map(items => this.items = items)
      .catch(() => this.toastr.error(`Problème de communication avec le serveur`, `Erreur`));
  }

  showOverQuotaModal(): void {
    this.childModal.show();
  }

  hideOverQuotaModal(): void {
    this.childModal.hide();
  }

  borrowOverQuota() {
    this.overQuota = true;
    this.hideOverQuotaModal();
    this.onSubmit();
  }

  onSubmit() {
    this.isExecutingLoan = true;
    if (this.isOverQuota()) {
      this.showOverQuotaModal();
    } else {
      if (this.selectedItem && !this.isItemLoading) {
        let loan = this.initializeLoan();
        loan.item.id = this.selectedItem.id;
        this.createLoan(loan);
      } else {
        this.resolveLoanWithItemArray(1);
      }
    }
  }

  updateIsItemLoading(event: boolean): void {
    this.isItemLoading = event;
  }

  setSelectedItem(selectedOption: TypeaheadMatch): void {
    this.selectedItem = selectedOption.item;
  }

  resetInputs(inputName: string): void {
    if (inputName !== this.lastFocus) {
      this.title = this.internalId = this.serialNumber = null;
      this.selectedItem = null;
      this.lastFocus = inputName;
    }
  }

  private getItemsByTitle(mainTitle: string): Observable<Item[]> {
    let filter: ItemFilter = new ItemFilter();
    filter.mainTitle = mainTitle;

    return this.itemService.findWithFilters(new Pageable('mainTitle', this.page, this.size), filter)
      .map(response => response.content)
      .catch(error => Observable.throw(error));
  }

  private getItemsBySerialNumber(serialNumber: string): Observable<Item[]> {
    let filter: ItemFilter = new ItemFilter();
    filter.serialType = 'ISBN';
    filter.serialNumber = serialNumber;

    return this.itemService.findWithFilters(new Pageable('serialNumber', this.page, this.size), filter)
      .map(response => response.content ? response.content : [])
      .catch(error => Observable.throw(error));
  }

  private getItemsByInternalId(internalId: number): Observable<Item[]> {
    return this.itemService.searchItemByInternalId(internalId)
      .map(item => item ? [item] : [])
      .catch(error => Observable.throw(error));
  }

  private isOverQuota(): boolean {
    this.overQuota = this.dataService.borrower.loans != null
      && this.dataService.borrower.loans.length + 1 > this.dataService.borrower.quota
      && this.overQuota === false;

    return this.overQuota;
  }

  private resolveLoanWithItemArray(tryNb: number): void {
    if (this.isItemLoading && tryNb < 10) {
      setTimeout(this.resolveLoanWithItemArray(tryNb + 1), 300);
    } else {
      if (this.items) {
        if (this.items.length === 0) {
          this.toastr.warning('Veuillez sélectionner un document', 'Emprunt impossible');
          this.isExecutingLoan = false;
        } else if (this.items.length > 1) {
          this.toastr.warning('Veuillez sélectionner un document parmi ceux proposés', 'Emprunt impossible');
          this.isExecutingLoan = false;
        } else if (this.items.length === 1) {
          let loan = this.initializeLoan();
          this.selectedItem = this.items[0];
          loan.item.id = this.selectedItem.id;
          this.createLoan(loan);
        }
      } else {
        this.toastr.warning('Veuillez sélectionner un document', 'Emprunt impossible');
        this.isExecutingLoan = false;
      }
    }
  }

  private initializeLoan(): Loan {
    let loan: Loan = new Loan();
    loan.item = new Item();
    loan.borrower = new Borrower();
    loan.returned = false;
    loan.borrower.id = this.dataService.borrower.id;

    return loan;
  }

  private createLoan(loan: Loan): void {
    this.loanService.createLoan(loan)
      .catch(err => {
        this.toastr.warning(`Le document a déjà été emprunté`, 'Emprunt');
        this.isExecutingLoan = false;

        return Observable.throw(err);
      })
      .subscribe(response => {
        this.loanService.find(response.json().id).then(succes => {
          if (!this.dataService.borrower.loans) {
            this.dataService.borrower.loans = [];
          }
          this.dataService.borrower.loans.push(succes);
        });
        this.toastr.success(`Le document a bien été emprunté`, 'Emprunt');
        this.isExecutingLoan = false;
      });
  }
}
