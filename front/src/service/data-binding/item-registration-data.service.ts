import {Injectable} from '@angular/core';
import {Record} from '../../entity/record/record';

@Injectable()
export class ItemRegistrationDataService {
  private record: Record;
  private searchMessage: string;
  private searchResults: Record[];
  private searching: boolean;

  getRecord(): Record {
    return this.record;
  }

  setRecord(value: Record) {
    this.record = value;
  }

  getSearchMessage(): string {
    return this.searchMessage;
  }

  setSearchMessage(value: string) {
    this.searchMessage = value;
  }

  getSearchResults(): Record[] {
    return this.searchResults;
  }

  setSearchResults(value: Record[]) {
    this.searchResults = value;
  }

  isSearching(): boolean {
    return this.searching;
  }

  setIsSearching(value: boolean) {
    this.searching = value;
  }
}
