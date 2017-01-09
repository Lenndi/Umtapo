import {Component} from '@angular/core';
import {ItemRegistrationDataService} from '../../../../../service/data-binding/item-registration-data.service';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.scss']
})
export class SearchResultComponent {
  constructor(public dataService: ItemRegistrationDataService) { }
}
