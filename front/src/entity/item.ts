import {ShelfMark} from './shelfmark';
import {Loan} from './loan';
export class Item {
  id: number;
  type: ItemType;
  shelfmark: ShelfMark;
  internalId: number;
  purchasePrice: number;
  loanable: boolean;
  loan: Loan[];
  condition: Condition;
  currency: string;
  library: number;
  recordId: string;
}
