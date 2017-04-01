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
  libraryId: number;
  libraryName: string;
  nbLoans: number;
  tooMuchLoans: boolean;
  lateness: boolean;

  constructor() {
    this.subscriptions = [];
    this.loans = [];
  }
}
