import {Injectable} from '@angular/core';
import {Borrower} from '../../entity/borrower';
import {Subscription} from '../../entity/subscription';

@Injectable()
export class NewBorrowerDataService {
  borrower: Borrower;
  subscription: Subscription;
  step: number;
  totalStep: number = 2;
  title: string;

  public flush(): void {
    this.borrower = null;
    this.subscription = null;
  }
}
