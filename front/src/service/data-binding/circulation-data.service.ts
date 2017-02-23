import {Injectable} from '@angular/core';
import {Borrower} from '../../entity/borrower';
import {Loan} from '../../entity/loan';

@Injectable()
export class CirculationDataService {
  borrower: Borrower;
}
