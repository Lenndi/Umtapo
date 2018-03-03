import {Component} from '@angular/core';
import {ItemRegistrationDataService} from '../../../../../../service/data-binding/item-registration-data.service';
import {RecordDetailFormatterService} from '../../../../../../service/record-detail-formatter.service';

@Component({
  selector: 'umt-external-item-details',
  templateUrl: './external-item-details.component.html',
  styleUrls: ['./external-item-details.component.scss']
})
export class ExternalItemDetailsComponent {

  constructor(public dataService: ItemRegistrationDataService,
              public formatterService: RecordDetailFormatterService) {}
}
