import { Component } from '@angular/core';
import {ItemRegistrationDataService} from '../../../service/data-binding/item-registration-data.service';
import {RecordService} from '../../../service/record.service';

@Component({
  selector: 'umt-item-registration',
  templateUrl: './item-registration.component.html',
  styleUrls: ['./item-registration.component.scss'],
  providers: [ItemRegistrationDataService, RecordService]
})
export class ItemRegistrationComponent {
}
