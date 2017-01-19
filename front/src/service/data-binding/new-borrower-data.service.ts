import {Injectable} from '@angular/core';
import {Borrower} from '../../entity/borrower';

@Injectable()
export class NewBorrowerDataService {
  borrower: Borrower;
  step: number;
  totalStep: number = 2;
  title: string;
}
