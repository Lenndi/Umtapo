import {Injectable} from '@angular/core';
import {Borrower} from '../../entity/borrower';
import {Subject} from 'rxjs';
import {Action} from '../../enumeration/Action';

@Injectable()
export class BorrowerDataService {

  private selectedBorrowerSource = new Subject<Borrower>();
  private updatedBorrowerSource = new Subject<Borrower>();
  public isEditShown: boolean = false;
  public isDeleteShown: boolean = false;
  public isRenewalShown: boolean = false;

  selectedBorrower$ = this.selectedBorrowerSource.asObservable();
  updatedBorrower$ = this.updatedBorrowerSource.asObservable();

  public changeSelectedBorrower(borrower: Borrower, action: Action) {
    switch (action) {
      case Action.EDIT:
        this.isEditShown = true;
        break;
      case Action.DELETE:
        this.isDeleteShown = true;
        break;
      case Action.RENEWAL:
        this.isRenewalShown = true;
        break;
      default:
        console.debug('Argument action is missing…');
    }
    this.selectedBorrowerSource.next(borrower);
  }

  public notifyUpdatedBorrower(borrower: Borrower) {
    this.updatedBorrowerSource.next(borrower);
  }
}
