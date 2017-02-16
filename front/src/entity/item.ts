import {ShelfMark} from './shelfmark';
import {Loan} from './loan';
import {Library} from './library';
import {Record} from './record/record';
import {Condition} from "./condition";
export class Item {
  id: number;
  type: string;
  shelfmark: ShelfMark;
  internalId: number;
  purchasePrice: number;
  loanable: boolean;
  loans: Loan[];
  condition: Condition;
  currency: string;
  library: Library;
  record: Record;

  constructor () {
    this.loans = [];
    this.record = new Record;
  }
}
