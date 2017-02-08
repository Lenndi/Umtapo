import { Component, OnInit } from '@angular/core';
import {ItemService} from "../../../../service/item.service";
import {CirculationDataService} from "../../../../service/data-binding/circulation-data.service";
import {Borrower} from "../../../../entity/borrower";
import {Observable, Subject} from "rxjs";
import {Item} from "../../../../entity/item";
import {BorrowerService} from "../../../../service/borrower.service";

@Component({
  selector: 'umt-circulation-check-out',
  templateUrl: './circulation-check-out.component.html',
  styleUrls: ['./circulation-check-out.component.scss'],
  providers: [ItemService]

})
export class CirculationCheckOutComponent implements OnInit {
  private borrower: Borrower;
  items: Observable<Item[]>;
  selectedItem: Item;
  page: number = 0;
  size: number = 10;
  internalId: number;
  private searchItems = new Subject<string>();


  constructor(private itemService: ItemService,
              public dataService: CirculationDataService,
              private borrowerService: BorrowerService) {
    this.selectedItem = new Item();
  }

  search(contains: string): void {
    this.searchItems.next(contains);
  }

  ngOnInit() {
    this.items = this.searchItems
      .debounceTime(200)
      .distinctUntilChanged()
      .switchMap(contains => contains ?
        this.itemService.findPaginableBySerialNumber(this.size, this.page, contains)
        : Observable.of<Item[]>([]))
      .catch(error => {
        return Observable.of<Item[]>([]);
      });
  }

  checkout(){
    this.itemService.setLoanAndItemCheckOut(this.internalId, this.borrower.id)
  }
}
