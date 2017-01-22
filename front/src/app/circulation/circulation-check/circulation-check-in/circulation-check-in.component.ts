import { Component, OnInit } from '@angular/core';
import {CirculationDataService} from "../../../../service/data-binding/circulation-data.service";
import {Borrower} from "../../../../entity/borrower";

@Component({
  selector: 'umt-circulation-check-in',
  templateUrl: './circulation-check-in.component.html',
  styleUrls: ['./circulation-check-in.component.scss']
})
export class CirculationCheckInComponent implements OnInit {
  private borrower: Borrower;

  constructor(public dataService: CirculationDataService) { }

  ngOnInit() {
    this.borrower = this.dataService.borrower;
  }

}
