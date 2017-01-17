import {Component} from '@angular/core';
import {NewBorrowerDataService} from '../../../service/data-binding/new-borrower-data.service';

@Component({
  selector: 'app-new-borrower',
  templateUrl: 'new-borrower.component.html',
  styleUrls: ['new-borrower.component.scss'],
  providers: [NewBorrowerDataService]
})

export class NewBorrowerComponent {
  constructor(public dataService: NewBorrowerDataService) {}
}
