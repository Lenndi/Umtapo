import { Component, OnInit } from '@angular/core';
import {SetupDataService} from '../../service/data-binding/setup-data.service';

@Component({
  selector: 'app-setup',
  templateUrl: './setup.component.html',
  styleUrls: ['./setup.component.scss'],
  providers: [SetupDataService]
})
export class SetupComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
