import {Component} from '@angular/core';
import {ItemService} from '../../../service/item.service';
import {ItemDatatableDataService} from '../../../service/data-binding/item-datatable-data.service';

@Component({
  selector: 'umt-item-management',
  templateUrl: './item-management.component.html',
  styleUrls: ['./item-management.component.scss'],
  providers: [ItemService, ItemDatatableDataService]
})
export class ItemManagementComponent {
}
