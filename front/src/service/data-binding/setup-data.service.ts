import { Injectable } from '@angular/core';
import {Library} from '../../entity/library';

@Injectable()
export class SetupDataService {
  library: Library;
  step: number;
  totalStep: number = 2;
  itemStartNumber: number;
  title: string;
}
