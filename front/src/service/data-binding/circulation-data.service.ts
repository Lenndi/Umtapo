import {Injectable} from '@angular/core';
import {Borrower} from '../../entity/borrower';

@Injectable()
export class CirculationDataService {
  borrower: Borrower;
}
