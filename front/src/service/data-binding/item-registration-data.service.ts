import {Injectable} from '@angular/core';
import {Record} from '../../entity/record/record';

@Injectable()
export class ItemRegistrationDataService {
  private record: Record;
  private searchMessage: string;

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
}
