import {Component, OnInit} from '@angular/core';
import {ORDER, Pageable} from '../../../../util/pageable';
import {Item} from '../../../../entity/item';
import {Page} from '../../../../util/page';
import {Subject} from 'rxjs/Subject';
import {ItemFilter} from '../../../../service/filter/item-filter';
import {Subscription} from 'rxjs/Subscription';
import {ItemService} from '../../../../service/item.service';
import {logger} from '../../../../environments/environment';
import {ItemDatatableDataService} from '../../../../service/data-binding/item-datatable-data.service';

@Component({
  selector: 'umt-item-datatable',
  templateUrl: './item-datatable.component.html',
  styleUrls: ['./item-datatable.component.scss']
})
export class ItemDatatableComponent implements OnInit {

  pageable: Pageable;
  page: Page<Item>;
  searchTerms = new Subject<ItemFilter>();
  itemFilter: ItemFilter;
  updatedItemSubscription: Subscription;

  public constructor(private itemService: ItemService, private dataService: ItemDatatableDataService) {
    this.itemFilter = new ItemFilter();
    this.itemFilter.complexSearch = true;
    this.pageable = new Pageable('mainTitle');

    this.updatedItemSubscription = this.dataService.updatedItem$.subscribe(
      item => this.changeFilter()
    );
  }

  ngOnInit(): void {
    this.searchTerms
      .debounceTime(200)
      .switchMap(itemFilter => this.itemService.findWithFilters(this.pageable, itemFilter))
      .subscribe(
        response => this.page = response,
        error => logger.error(error)
      );

    this.changeFilter();
  }

  changeFilter(): void {
    this.searchTerms.next(this.itemFilter);
  }

  changePage(page: number): void {
    this.pageable.page = page;
    this.changeFilter();
  }

  changeSort(sort: string): void {
    if (this.pageable.sort != sort) {
      this.pageable.sort = sort;
    } else {
      if (this.pageable.order === ORDER.ASC) {
        this.pageable.order = ORDER.DESC;
      } else {
        this.pageable.order = ORDER.ASC;
      }
    }
    this.changeFilter();
  }

  pageIndex(): any[] {
    let pageIndex: any[] = [];

    if (this.page) {
      if (this.page.totalPages < 11) {
        for (let i = 0; i < this.page.totalPages; i++) {
          pageIndex.push(i);
        }
      } else {
        if (this.page.number < 5) {
          for (let i = 0; i < 5; i++) {
            pageIndex.push(i);
          }
          pageIndex.push(-2);
        } else if (this.page.number > this.page.totalPages - 5) {
          pageIndex.push(-1);
          for (let i = this.page.totalPages - 5; i < this.page.totalPages; i++) {
            pageIndex.push(i);
          }
        } else {
          pageIndex.push(-1);
          for (let i = this.page.number - 2; i <= this.page.number + 2; i++) {
            pageIndex.push(i);
          }
          pageIndex.push(-2);
        }
      }
    }

    return pageIndex;
  }

  onEditItem(itemId: number): void {
    this.notifyItem(itemId);
  }

  isCurrentPage(pageIndex: number): boolean {
    return pageIndex === this.page.number;
  }

  notifyItem(itemId: number) {
    this.itemService.find(itemId)
      .then(item => {
        this.dataService.changeSelectedItem(item);
      })
      .catch(error => {
        logger.error(error);
      });
  }
}
