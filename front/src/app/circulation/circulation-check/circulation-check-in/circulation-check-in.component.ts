import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {CirculationDataService} from '../../../../service/data-binding/circulation-data.service';
import {Loan} from '../../../../entity/loan';
import {conditionEnum} from '../../../../enumeration/fr';
import {CustomMap} from '../../../../enumeration/custom-map';
import {ItemService} from '../../../../service/item.service';
import {Item} from '../../../../entity/item';
import {LoanService} from '../../../../service/loan.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'umt-circulation-check-in',
  templateUrl: './circulation-check-in.component.html',
  styleUrls: ['./circulation-check-in.component.scss'],
  providers: [ItemService, LoanService]
})
export class CirculationCheckInComponent implements OnInit {
  @ViewChild('loanListContainer') loanListContainer:  ElementRef;
  @ViewChild('loanList') loanList: ElementRef;
  loanListContainerHeight: number;
  conditionEnum: CustomMap;
  internalId: number;
  serial: string;
  isExecutingLoan: boolean;

  constructor(public dataService: CirculationDataService,
              private itemService: ItemService,
              private loanService: LoanService,
              public toastr: ToastrService) {
    this.conditionEnum = conditionEnum;
  }

  ngOnInit() {
    this.loanService.findAllDtoByBorrowerIdAndReturned(this.dataService.borrower.id)
      .then(response => this.dataService.borrower.loans = response);
  }

  setLoanListHeight(): void {
    const loanListHeight = this.loanList.nativeElement.getBoundingClientRect().height;
    const maxHeight = window.innerHeight - this.loanListContainer.nativeElement.getBoundingClientRect().top - 30;

    if (loanListHeight <= maxHeight) {
      this.loanListContainerHeight = loanListHeight;
    } else {
      this.loanListContainerHeight = maxHeight;
    }
  }

  saveCondition(value, id) {
    let item: Item = new Item();
    item.condition = value;
    item.id = id;
    this.itemService.saveCondition(item)
      .then(res => this.toastr.success(`La condition du livre à été modifier à ` + value, 'Sauvegarder'))
      .catch(err => this.toastr.error(`Une erreur est survenue`, 'Erreur'));
  }

  saveEnd(value, id) {
    let loan: Loan = new Loan();
    let date = new Date(value);
    loan.end = new Date(date.toISOString());
    loan.id = id;
    this.loanService.saveEnd(loan)
      .then(res => this.toastr.success(`La date de retour à été modifier à ` + value, 'Sauvegarder'))
      .catch(err => this.toastr.error(`Une erreur est survenue`, 'Erreur'));
  }

  returnAllBooks() {
    if (this.dataService.borrower.loans && this.dataService.borrower.loans.length != 0) {
      for (let loan of this.dataService.borrower.loans) {
        this.checkInDocument(loan);
      }
      this.dataService.borrower.loans = [];
    } else {
      this.toastr.warning(`Vous n'avez aucun livre à retourner`, 'Pas de document');
    }
  }

  returnBook(loan: Loan) {
    if (this.dataService.borrower.loans) {
      this.checkInDocument(loan);
      this.removeLoanById(loan.id);
    } else {
      this.toastr.success(`Vous n'avez aucun livre à retourner`, 'Pas de document');
    }
  }

  checkInDocument(loan: Loan) {
    this.isExecutingLoan = true;
    this.loanService.returnBookLoan(loan.id)
      .then(() => this.itemService.returnBookItem(loan.item.id)
        .then(() => this.toastr.success(
            `Le document ${loan.item.record.title.mainTitle} a bien été retourné`,
            'Document retourné'))
        .catch(() => this.toastr.error(`Une erreur est survenue lors du retour du document`, 'Erreur retour')))
      .catch(() => this.toastr.error(`Une erreur est survenue lors du retour du document`, 'Erreur retour'))
      .then(() => this.isExecutingLoan = false);
  }

  removeLoanById(id: number) {
    for (let i = 0; i < this.dataService.borrower.loans.length; i++) {
      if (this.dataService.borrower.loans[i].id === id) {
        this.dataService.borrower.loans.splice(i, 1);
      }
    }
  }

  checkBySerialOrInternalId() {
    let isLoan = false;
    if (this.internalId != null) {
      for (let loan of this.dataService.borrower.loans) {
        if (loan.item.internalId === this.internalId) {
          isLoan = true;
          this.checkInDocument(loan);
          this.removeLoanById(loan.id);
        }
      }
      if (!isLoan) {
        this.toastr.warning(`L'identifiant que vous fournissez ne correspond a aucun document`, 'Pas de document');
      }
    } else if (this.serial != null) {
      if (this.dataService.borrower.loans != null) {
        let selectedLoan;
        let cnt = 0;
        for (let loan of this.dataService.borrower.loans) {
          if (loan.item.record.identifier.serialNumber === this.serial) {
            selectedLoan = loan;
            cnt++;
          }
        }
        if (cnt === 1) {
          this.checkInDocument(selectedLoan);
          this.removeLoanById(selectedLoan.id);
        } else if (cnt > 1) {
          this.toastr.warning(
            `Vous avez plusieurs documents avec le même numéro de série, veuillez renseigner l'id interne`,
            'Multiples documents');
        } else {
          this.toastr.warning(`L'ISBN que vous fournissez ne correspond a aucun document`, 'Pas de document');
        }
      } else {
        this.toastr.success(`Vous n'avez aucun livre à retourner`, 'Pas de document');
      }
    } else {
      this.toastr.warning(`Vous n'avez renseigné aucun champ`, 'Champs vides');
    }
  }
}
