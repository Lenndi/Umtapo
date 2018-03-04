import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Item} from '../../entity/item';

@Injectable()
export class ItemDatatableDataService {

  private selectedItemSource = new Subject<Item>();
  private updatedItemSource = new Subject<Item>();
  public isEditShown = false;
  public isExternalItem;

  selectedItem$ = this.selectedItemSource.asObservable();
  updatedItem$ = this.updatedItemSource.asObservable();

  public changeSelectedItem(item: Item) {
    this.selectedItemSource.next(item);
    this.isExternalItem = item.externalLibrary != null;
    this.isEditShown = true;
  }

  public notifyUpdatedItem(item: Item) {
    this.updatedItemSource.next(item);
  }
}
