import {Component, OnInit} from '@angular/core';
import {CirculationDataService} from "../../../../service/data-binding/circulation-data.service";
import {Borrower} from "../../../../entity/borrower";
import {DatepickerModule} from 'ng2-bootstrap';
import {DateFormatter} from "@angular/common/src/pipes/intl";
import {Loan} from "../../../../entity/loan";
import {conditionEnum} from '../../../../enumeration/fr';
import {CustomMap} from "../../../../enumeration/custom-map";
import {ItemService} from "../../../../service/item.service";
import {Item} from "../../../../entity/item";
import {LoanService} from "../../../../service/loan.service";

@Component({
  selector: 'umt-circulation-check-in',
  templateUrl: './circulation-check-in.component.html',
  styleUrls: ['./circulation-check-in.component.scss'],
  providers: [ItemService, LoanService]
})
export class CirculationCheckInComponent implements OnInit {
  private borrower: Borrower;
  conditionEnum: CustomMap;
  itemId: number;
  serial: string;


  constructor(public dataService: CirculationDataService,
              private itemService: ItemService,
              private loanService: LoanService) {
    this.conditionEnum = conditionEnum;
  }

  ngOnInit() {
    this.borrower = this.dataService.borrower;
    this.loanService.findAllDtoByBorrowerIdAndReturned(this.borrower.id)
      .then(response => {
        this.borrower.loans = response;
      });

  }

  saveCondition(value, id) {
    let item: Item = new Item();
    item.condition = value;
    item.id = id;
    this.itemService.saveCondition(item);
    // TODO Toast modification
  }

  saveEnd(value, id) {
    let loan: Loan = new Loan();
    let date = new Date(value);
    loan.end = new Date(date.toISOString());
    loan.id = id;
    this.loanService.saveEnd(loan);
    // TODO Toast modification
  }

  returnAllBooks(){
    for (let loan of this.borrower.loans){
      this.loanService.returnBookLoan(loan.id);
      this.itemService.returnBookItem(loan.item.id);
    }
  }

  returnBook(idLoan, idItem) {
    this.loanService.returnBookLoan(idLoan);
    this.itemService.returnBookItem(idItem);
  }

  checkBySerialOrInternalId() {
    if (this.itemId !== null) {
      for (let loan of this.borrower.loans) {
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
      for (let loan of this.borrower.loans) {
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
