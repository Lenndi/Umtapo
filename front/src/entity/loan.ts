import {Item} from "./item";
import {Borrower} from "./borrower";

export class Loan {
  id: number;
  start: Date;
  end: Date;
  borrower: Borrower;
  item: Item;
}
