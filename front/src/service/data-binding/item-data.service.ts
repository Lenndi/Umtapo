import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Item} from '../../entity/item';

@Injectable()
export class ItemDataService {

  private selectedItemSource = new Subject<Item>();
  private updatedItemSource = new Subject<Item>();

  selectedItem$ = this.selectedItemSource.asObservable();
  updatedItem$ = this.updatedItemSource.asObservable();

  public changeSelectedItem(item: Item) {
    this.selectedItemSource.next(item);
  }

  public notifyUpdatedItem(item: Item) {
    this.updatedItemSource.next(item);
  }
}
