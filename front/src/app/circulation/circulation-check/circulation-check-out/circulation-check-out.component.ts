import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {ItemService} from '../../../../service/item.service';
import {CirculationDataService} from '../../../../service/data-binding/circulation-data.service';
import {Borrower} from '../../../../entity/borrower';
import {Observable, Subject, Observer} from 'rxjs';
import {Item} from '../../../../entity/item';
import {BorrowerService} from '../../../../service/borrower.service';
import {TypeaheadMatch} from 'ng2-bootstrap';
import {ToastsManager} from 'ng2-toastr';
import {Response, RequestOptions} from "@angular/http";
import {Loan} from "../../../../entity/loan";
import {LoanService} from "../../../../service/loan.service";

@Component({
  selector: 'umt-circulation-check-out',
  templateUrl: './circulation-check-out.component.html',
  styleUrls: ['./circulation-check-out.component.scss'],
  providers: [ItemService]

})
export class CirculationCheckOutComponent implements OnInit {
  private borrower: Borrower;
  items: Item[] = [];
  selectedItem: Item;
  page: number = 0;
  size: number = 10;
  internalId: number;
  private searchItems = new Subject<string>();
  barCode: string;
  public dataSource: Observable<Item[]>;

  constructor(private itemService: ItemService,
              private loanService: LoanService,
              public dataService: CirculationDataService,
              private borrowerService: BorrowerService,
              public toastr: ToastsManager, vRef: ViewContainerRef) {

    this.toastr.setRootViewContainerRef(vRef);
    this.selectedItem = new Item();

    this.dataSource = Observable
      .create((observer: any) => {
        // Runs on every search
        observer.next(this.barCode);
      })
      .switchMap((contains: string) => this.itemService.findPaginableByContains(this.size, this.page, contains, "barCode"))
      .map(res => this.items = res as Item[]);
  }

  ngOnInit() {
    this.borrower = this.dataService.borrower;
  }

  public typeaheadOnSelect(itemTypeahead: TypeaheadMatch): void {
    this.selectedItem = itemTypeahead.item;
  }

  public changeTypeaheadNoResults(e: boolean): void {
    if (!this.barCode) {
      this.items = [];
    }
  }

  checkout() {

    let loan: Loan = new Loan();
    let item: Item = new Item();

    loan.item = new Item();
    loan.borrower = new Borrower();
    loan.returned = false;
    loan.borrower.id = this.borrower.id;

    if (this.internalId) {
      let observable = this.itemService.searchItemByInternalId(this.internalId)
        .catch(err => {
          this.toastr.error(`Un problème est survenu le document ne peut être emprunté`, 'Problème', {toastLife: 2000});
          return Observable.throw(err); // observable needs to be returned or exception raised
        })
        .map(res => {
          if (res.status == 204) {
            console.log(res.json());
            this.toastr.warning(`Aucun document ne comporte cet identifiant`, 'Non trouvé', {toastLife: 2000});
            observable.unsubscribe();
          }
          item = res.json();
          loan.item.id = item.id;
        }).subscribe(x => {
          this.BorrowDocument(item, loan)
        });
    } else if (this.selectedItem) {
      item = this.selectedItem;
      loan.item.id = item.id;
      this.BorrowDocument(item, loan);

    } else if (this.items) {
      if (this.items.length == 0) {
        if (this.barCode) {
          this.toastr.warning(`Aucun document n'est lié à cette information`, 'Pas de document!', {toastLife: 2000});
        } else {
          this.toastr.warning('Vous devez entrer une information pour emprunter un livre.', 'Champs vides!', {toastLife: 2000});
        }
      } else if (this.items.length > 1) {
        this.toastr.warning('Plusieurs documents ont ce numéro de série, vous devez en sélectionner un.', 'Plusieurs documents existants!', {toastLife: 2000});
      } else if (this.items.length == 1) {

      }
    }
  }

  public BorrowDocument(itemBorrow: Item, loanBorrow: Loan) {
    let observable = this.itemService.patchCheckoutItem(itemBorrow.id)
      .catch(err => {
        this.toastr.error(`Un problème est survenu le document ne peut être emprunté`, 'Problème', {toastLife: 2000});
        return Observable.throw(err);
      })
      .map(res => {
        if (res.status != 200) {
          if (res.status == 202) {
            if (res.json() == 1000) {
              this.toastr.warning(`Le document a déjà été emprunté`, 'Non trouvé', {toastLife: 2000});
            }
          } else if (res.status == 204) {
            this.toastr.warning(`Aucun document ne comporte cet identifiant`, 'Non trouvé', {toastLife: 2000});
          }
          observable.unsubscribe();
        }
      })
      .flatMap(item => this.loanService.createLoan(loanBorrow)
        .catch(err => {
          this.toastr.error(`Un problème est survenu le document ne peut être emprunté`, 'Problème', {toastLife: 2000});
          return Observable.throw(err); // observable needs to be returned or exception raised
        }))
      .subscribe(response => this.toastr.success(`Le document a bien été emprunté`, 'Emprunt réussi', {toastLife: 2000}));
  }
}
