import {Component, OnInit} from '@angular/core';
import {Borrower} from '../../../entity/borrower';
import {BorrowerService} from '../../../service/borrower.service';
import {BorrowerFilter} from '../../../service/various/borrower-filter';
import {Subject} from 'rxjs';
import {Pageable, ORDER} from '../../../util/pageable';
import {Page} from '../../../util/page';

@Component({
  selector: 'umt-borrowers-management',
  templateUrl: './borrowers-management.component.html',
  styleUrls: ['./borrowers-management.component.scss'],
  providers: [BorrowerService]
})
export class BorrowersManagementComponent implements OnInit {

  pageable: Pageable;
  page: Page<Borrower>;
  searchTerms = new Subject<BorrowerFilter>();
  borrowerFilter: BorrowerFilter;

  public constructor(private borrowerService: BorrowerService) {
    this.borrowerFilter = new BorrowerFilter();
    this.borrowerFilter.id = '';
    this.borrowerFilter.email = '';
    this.borrowerFilter.name = '';
    this.borrowerFilter.city = '';

    this.pageable = new Pageable();
    this.pageable.page = 0;
    this.pageable.size = 10;
    this.pageable.sort = 'id';
    this.pageable.order = ORDER.ASC;
  }

  ngOnInit(): void {
    this.searchTerms
      .debounceTime(200)
      .switchMap(borrowerFilter => this.borrowerService.findWithFilters(this.pageable, borrowerFilter))
      .subscribe(
        response => this.page = response,
        error => console.debug(error)
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

  onCellClick(): void {
    console.debug('cell click');
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
}
