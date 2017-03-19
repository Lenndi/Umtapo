import {Address} from './address';
import {Subscription} from './subscription';
import {Loan} from './loan';

export class Borrower {
  id: number;
  name: string;
  birthday: Date;
  quota: number;
  emailOptin: boolean;
  comment: string;
  address: Address;
  subscriptions: Subscription[];
  loans: Loan[];

  constructor() {
    this.subscriptions = [];
    this.loans = [];
  }
}
