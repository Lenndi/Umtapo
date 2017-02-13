import { Component, OnInit } from '@angular/core';
import {ItemService} from '../../../../service/item.service';
import {CirculationDataService} from '../../../../service/data-binding/circulation-data.service';
import {Borrower} from '../../../../entity/borrower';
import {Observable, Subject, Observer} from 'rxjs';
import {Item} from '../../../../entity/item';
import {BorrowerService} from '../../../../service/borrower.service';
import {TypeaheadMatch} from "ng2-bootstrap";

@Component({
  selector: 'umt-circulation-check-out',
  templateUrl: './circulation-check-out.component.html',
  styleUrls: ['./circulation-check-out.component.scss'],
  providers: [ItemService]

})
export class CirculationCheckOutComponent implements OnInit {
  private borrower: Borrower;
  items : Item[];
  selectedItem: Item;
  page: number = 0;
  size: number = 10;
  internalId: number;
  private searchItems = new Subject<string>();
  barCode: string;
  public dataSource: Observable<Item[]>;

  constructor(private itemService: ItemService,
              public dataService: CirculationDataService,
              private borrowerService: BorrowerService) {

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
    this.itemService.setLoanAndItemCheckOut(this.internalId, this.borrower.id);
  }

  public changeTypeaheadNoResults(e: boolean): void {
    if(!this.barCode){
      this.items = [];
    }
  }

  checkout(){
    if(this.items.length > 0){
      if(this.items.length > 1){

      }
    }
  }
}
