import {Component} from '@angular/core';
import {NewBorrowerDataService} from '../../../service/data-binding/new-borrower-data.service';
import {NewBorrower} from './new-borrower.interface';

@Component({
  selector: 'app-new-borrower',
  templateUrl: './new-borrower.component.html',
  styleUrls: ['./new-borrower.component.scss'],
  providers: [NewBorrowerDataService]
})

export class NewBorrowerComponent {
  newBorrowerChild: NewBorrower;

  constructor(public dataService: NewBorrowerDataService) {}

  saveData() {
    this.newBorrowerChild.saveData();
  }
}
