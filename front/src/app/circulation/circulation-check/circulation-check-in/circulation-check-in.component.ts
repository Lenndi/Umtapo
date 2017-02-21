import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {CirculationDataService} from '../../../../service/data-binding/circulation-data.service';
import {Borrower} from '../../../../entity/borrower';
import {DatepickerModule} from 'ng2-bootstrap';
import {DateFormatter} from '@angular/common/src/pipes/intl';
import {Loan} from '../../../../entity/loan';
import {conditionEnum} from '../../../../enumeration/fr';
import {CustomMap} from '../../../../enumeration/custom-map';
import {ItemService} from '../../../../service/item.service';
import {Item} from '../../../../entity/item';
import {LoanService} from '../../../../service/loan.service';
import {ToastsManager} from 'ng2-toastr';

@Component({
  selector: 'umt-circulation-check-in',
  templateUrl: './circulation-check-in.component.html',
  styleUrls: ['./circulation-check-in.component.scss'],
  providers: [ItemService, LoanService]
})
export class CirculationCheckInComponent implements OnInit {
  conditionEnum: CustomMap;
  itemId: number;
  serial: string;


  constructor(public dataService: CirculationDataService,
              private itemService: ItemService,
              private loanService: LoanService,
              public toastr: ToastsManager, vRef: ViewContainerRef) {
    this.conditionEnum = conditionEnum;
    this.toastr.setRootViewContainerRef(vRef);
  }

  ngOnInit() {
    this.loanService.findAllDtoByBorrowerIdAndReturned(this.dataService.borrower.id)
      .then(response => {
        this.dataService.borrower.loans = response;
      });
  }

  saveCondition(value, id) {
    let item: Item = new Item();
    item.condition = value;
    item.id = id;
    this.itemService.saveCondition(item)
      .then(res => this.toastr.success(`La condition du livre à été modifier à ` + value, 'Sauvegarder',
        {toastLife: 2000}))
      .catch(err => this.toastr.error(`Une erreur est survenue`, 'Erreur', {toastLife: 2000}));
  }

  saveEnd(value, id) {
    let loan: Loan = new Loan();
    let date = new Date(value);
    loan.end = new Date(date.toISOString());
    loan.id = id;
    this.loanService.saveEnd(loan)
      .then(res => this.toastr.success(`La date de retour à été modifier à ` + value, 'Sauvegarder', {toastLife: 2000}))
      .catch(err => this.toastr.error(`Une erreur est survenue`, 'Erreur', {toastLife: 2000}));
  }

  returnAllBooks() {
    if (this.dataService.borrower.loans) {
      for (let loan of this.dataService.borrower.loans) {
        this.loanService.returnBookLoan(loan.id)
          .then(ret => this.itemService.returnBookItem(loan.item.id)
            .then(ret => this.toastr.success(`Tous les documents ont bien été retournés`, 'Documents retournés',
              {toastLife: 2000})
              .catch(err => this.toastr.error(`Une erreur est survenue lors du retour du document`,
                'Documents retournés', {toastLife: 2000})))
            .catch(err => this.toastr.error(`Une erreur est survenue lors du retour du document`, 'Documents retournés',
              {toastLife: 2000})));
      }
      this.dataService.borrower.loans = [];
    } else {
      this.toastr.success(`Vous n'avez aucun livre à retourner`, 'Pas de document', {toastLife: 2000});
    }
  }

  returnBook(idLoan, idItem) {
    if (this.dataService.borrower.loans) {
      this.loanService.returnBookLoan(idLoan)
        .then(ret => this.itemService.returnBookItem(idItem)
          .then(ret => this.toastr.success(`Tous les documents ont bien été retournés`, 'Documents retournés',
            {toastLife: 2000})
            .catch(err => this.toastr.error(`Une erreur est survenue lors du retour du document`, 'Documents retournés',
              {toastLife: 2000})))
          .catch(err => this.toastr.error(`Une erreur est survenue lors du retour du document`, 'Documents retournés',
            {toastLife: 2000})));
      this.removeLoanById(idLoan);
    } else {
      this.toastr.success(`Vous n'avez aucun livre à retourner`, 'Pas de document', {toastLife: 2000});
    }
  }

  removeLoanById(id: number) {
    for (let i = 0; i < this.dataService.borrower.loans.length; i++) {
      console.log(this.dataService.borrower.loans);
      if (this.dataService.borrower.loans[i].id == id) {
        this.dataService.borrower.loans.splice(i, 1);
      }
      console.log(this.dataService.borrower.loans);
    }
  }

  checkBySerialOrInternalId() {
    if (this.itemId !== null) {
      for (let loan of this.dataService.borrower.loans) {
        if (loan.item.internalId == this.itemId) {
          this.loanService.returnBookLoan(loan.id);
          this.itemService.returnBookItem(loan.item.id);
        } else {
          // TODO TOAST
        }
      }
    } else if (this.serial !== null) {
      let cnt = 0;
      let loanId;
      let itemId;
      for (let loan of this.dataService.borrower.loans) {
        if (loan.item.record.identifier.serialNumber == this.serial) {
          loanId = loan.id;
          itemId = loan.item.id;
          cnt++;
        }
      }
      if (cnt == 1) {
        this.loanService.returnBookLoan(loanId);
        this.itemService.returnBookItem(itemId);
        // TODO Toast
      } else if (cnt > 1) {
        // TODO Toast
      } else {
        // TODO Toast
      }
    } else {
      // TODO Toast
    }
  }
}
