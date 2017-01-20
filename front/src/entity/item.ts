import {ShelfMark} from './shelfmark';
import {Loan} from './loan';
import {Library} from './library';
import {Record} from './record/record';
export class Item {
  id: number;
  type: string;
  shelfmark: ShelfMark;
  internalId: number;
  purchasePrice: number;
  loanable: boolean;
  loan: Loan[];
  condition: string;
  currency: string;
  library: Library;
  record: Record;
}
