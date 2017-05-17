import {Component} from '@angular/core';
import {ItemRegistrationDataService} from '../../../../service/data-binding/item-registration-data.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-item-save',
  templateUrl: './item-save.component.html',
  styleUrls: ['./item-save.component.scss']
})
export class ItemSaveComponent {
  constructor(private dataService: ItemRegistrationDataService, private router: Router) {
    if (!this.dataService.record) {
      this.router.navigate(['cataloging/registration/changeFilter']);
    }
  }
}
