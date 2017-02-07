import { Component, OnInit } from '@angular/core';
import {ItemService} from "../../../../service/item.service";
import {CirculationDataService} from "../../../../service/data-binding/circulation-data.service";
import {Borrower} from "../../../../entity/borrower";

@Component({
  selector: 'umt-circulation-check-out',
  templateUrl: './circulation-check-out.component.html',
  styleUrls: ['./circulation-check-out.component.scss'],
  providers: [ItemService]

})
export class CirculationCheckOutComponent implements OnInit {
  private borrower: Borrower;
  internalId: number;

  constructor(private itemService: ItemService,
              public dataService: CirculationDataService) { }

  ngOnInit() {
    this.borrower = this.dataService.borrower;
  }

  checkout(){
    this.itemService.setLoanAndItemCheckOut(this.internalId, this.borrower.id)
  }
}
