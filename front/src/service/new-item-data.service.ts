import {Injectable} from '@angular/core';
import {Item} from '../entity/item';

@Injectable()
export class NewItemDataService {
  item: Item;
  isPairing = false;
}
