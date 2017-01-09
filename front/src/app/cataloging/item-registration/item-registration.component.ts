import { Component } from '@angular/core';
import {ItemRegistrationDataService} from '../../../service/data-binding/item-registration-data.service';

@Component({
  selector: 'app-item-registration',
  templateUrl: './item-registration.component.html',
  styleUrls: ['./item-registration.component.scss'],
  providers: [ItemRegistrationDataService]
})
export class ItemRegistrationComponent {
}
