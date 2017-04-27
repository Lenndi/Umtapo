import {Injectable} from '@angular/core';
import {Borrower} from '../../entity/borrower';
import {Subject} from 'rxjs';
import {Action} from '../../enumeration/Action';

@Injectable()
export class BorrowerDataService {

  private selectedBorrowerSource = new Subject<Borrower>();
  private updatedBorrowerSource = new Subject<Borrower>();
  public action: Action;

  selectedBorrower$ = this.selectedBorrowerSource.asObservable();
  updatedBorrower$ = this.updatedBorrowerSource.asObservable();

  public changeSelectedBorrower(borrower: Borrower) {
    this.selectedBorrowerSource.next(borrower);
  }

  public notifyUpdatedBorrower(borrower: Borrower) {
    this.updatedBorrowerSource.next(borrower);
  }
}
