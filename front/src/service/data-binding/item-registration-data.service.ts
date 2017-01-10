import {Injectable} from '@angular/core';
import {Record} from '../../entity/record/record';

@Injectable()
export class ItemRegistrationDataService {
  record: Record;
  searchMessage: string;
  searchResults: Record[];
  isSearching: boolean;
  hasMoreRecords: boolean;

  constructor() {
    this.searchResults = [];
  }
}
