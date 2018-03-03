import {Component} from '@angular/core';
import {ItemRegistrationDataService} from '../../../../../service/data-binding/item-registration-data.service';
import {Router} from '@angular/router';
import {RecordDetailFormatterService} from '../../../../../service/record-detail-formatter.service';

@Component({
  selector: 'umt-external-item-save',
  templateUrl: './external-item-save.component.html',
  styleUrls: ['./external-item-save.component.scss'],
  providers: [RecordDetailFormatterService]
})
export class ExternalItemSaveComponent {
  constructor(private dataService: ItemRegistrationDataService, private router: Router) {
    if (!this.dataService.record) {
      this.router.navigate(['cataloging/registration/search']);
    }
  }
}
