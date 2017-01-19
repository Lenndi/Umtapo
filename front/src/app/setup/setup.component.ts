import {Component} from '@angular/core';
import {SetupDataService} from '../../service/data-binding/setup-data.service';
import {Setup} from './setup.interface';

@Component({
  selector: 'umt-setup',
  templateUrl: './setup.component.html',
  styleUrls: ['./setup.component.scss'],
  providers: [SetupDataService]
})
export class SetupComponent {
  setupChild: Setup;

  constructor(public dataService: SetupDataService) {}

  saveData() {
    this.setupChild.saveData();
  }
}
