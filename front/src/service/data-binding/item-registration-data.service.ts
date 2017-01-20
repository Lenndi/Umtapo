import {Injectable} from '@angular/core';
import {Record} from '../../entity/record/record';
import {Item} from '../../entity/item';

@Injectable()
export class ItemRegistrationDataService {
  item: Item;
  record: Record;
  searchMessage: string;
  searchResults: Record[];
  isSearching: boolean;
  hasMoreRecords: boolean;

  constructor() {
    this.searchResults = [];
  }
}
