import {Component} from '@angular/core';
import {ItemService} from '../../../service/item.service';
import {ItemDataService} from '../../../service/data-binding/item-data.service';

@Component({
  selector: 'umt-item-management',
  templateUrl: './item-management.component.html',
  styleUrls: ['./item-management.component.scss'],
  providers: [ItemService, ItemDataService]
})
export class ItemManagementComponent {
}
