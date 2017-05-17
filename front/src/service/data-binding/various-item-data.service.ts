import {Item} from '../../entity/item';
import {Injectable} from '@angular/core';
import {Library} from '../../entity/library';

@Injectable()
export class VariousItemDataService {
  item: Item;
  externalLibraries: Library[];

  constructor() {
    this.externalLibraries = [];
  }
}
