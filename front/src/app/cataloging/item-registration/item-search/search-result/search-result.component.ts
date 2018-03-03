import {Component, OnInit} from '@angular/core';
import {ItemRegistrationDataService} from '../../../../../service/data-binding/item-registration-data.service';
import {Record} from '../../../../../entity/record/record';
import {Router} from '@angular/router';

@Component({
  selector: 'umt-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.scss']
})
export class SearchResultComponent implements OnInit {
  resultListHeight: number;

  constructor(public dataService: ItemRegistrationDataService, private router: Router) { }

  ngOnInit() {
    this.resultListHeight = document.getElementById('side-nav').clientHeight - 200;
  }

  onSelect(record: Record) {
    this.dataService.hasMoreRecords = false;
    this.dataService.record = record;
    this.router.navigate(['/cataloging/registration/save']);
  }
}
