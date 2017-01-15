import {Component, OnInit} from '@angular/core';
import {ItemRegistrationDataService} from '../../../../../service/data-binding/item-registration-data.service';

@Component({
  selector: 'umt-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.scss']
})
export class SearchResultComponent implements OnInit {
  resultListHeight: number;

  constructor(public dataService: ItemRegistrationDataService) { }

  ngOnInit() {
    this.resultListHeight = document.getElementById('side-nav').clientHeight - 200;
  }
}
