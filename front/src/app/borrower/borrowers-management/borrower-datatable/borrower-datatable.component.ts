import {Component, OnInit} from '@angular/core';
import {Borrower} from '../../../../entity/borrower';
import {BorrowerService} from '../../../../service/borrower.service';
import {BorrowerFilter} from '../../../../service/filter/borrower-filter';
import {Subject, Subscription} from 'rxjs';
import {ORDER, Pageable} from '../../../../util/pageable';
import {Page} from '../../../../util/page';
import {logger} from '../../../../environments/environment';
import {BorrowerDataService} from '../../../../service/data-binding/borrower-data.service';
import {Action} from '../../../../enumeration/Action';
import {PairingBorrowerButtonComponent} from '../../../pairing-borrower-button/pairing-borrower-button.component';
import {NewBorrowerDataService} from '../../../../service/data-binding/new-borrower-data.service';

@Component({
  selector: 'umt-borrower-datatable',
  templateUrl: 'borrower-datatable.component.html',
  styleUrls: ['borrower-datatable.component.scss']
})
export class BorrowerDatatableComponent implements OnInit {

  pageable: Pageable;
  page: Page<Borrower>;
  searchTerms = new Subject<BorrowerFilter>();
  borrowerFilter: BorrowerFilter;
  updatedBorrowerSubscription: Subscription;

  public constructor(private borrowerService: BorrowerService,
                     private newBorrowerDataService: NewBorrowerDataService,
                     private dataService: BorrowerDataService) {
    this.borrowerFilter = new BorrowerFilter();
    this.pageable = new Pageable('email');

    this.updatedBorrowerSubscription = this.dataService.updatedBorrower$.subscribe(
      borrower => this.changeFilter()
    );
  }

  ngOnInit(): void {
    this.searchTerms
      .debounceTime(200)
      .switchMap(borrowerFilter => this.borrowerService.findWithFilters(this.pageable, borrowerFilter))
      .subscribe(
        response => this.page = response,
        error => logger.error(error)
      );

    this.changeFilter();
  }

  changeFilter(): void {
    this.searchTerms.next(this.borrowerFilter);
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

  onEditBorrower(borrowerId: number): void {

    this.newBorrowerDataService.borrower = new Borrower;
    this.newBorrowerDataService.borrower.id = borrowerId;
    this.notifyBorrower(borrowerId, Action.EDIT);
  }

  onDeleteBorrower(borrowerId: number): void {
    this.notifyBorrower(borrowerId, Action.DELETE);
  }

  onRenewalSubscription(borrowerId: number): void {
    this.notifyBorrower(borrowerId, Action.RENEWAL);
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

  isCurrentPage(pageIndex: number): boolean {
    return pageIndex === this.page.number;
  }

  notifyBorrower(borrowerId: number, action: Action) {
    this.borrowerService.find(borrowerId)
      .then(borrower => {
        this.dataService.changeSelectedBorrower(borrower, action);
      })
      .catch(error => logger.error(error));
  }
}
