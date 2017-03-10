import {Borrower} from './borrower';
import {Library} from './library';
export class Subscription {
    id: number;
    start: Date;
    end: Date;
    contribution: number;
    borrower: Borrower;
    library: Library;
}
