import {Component} from '@angular/core';
import {CirculationDataService} from '../../service/data-binding/circulation-data.service';

@Component({
  selector: 'umt-circulation',
  templateUrl: './circulation.component.html',
  styleUrls: ['./circulation.component.scss'],
  providers: [CirculationDataService]
})
export class CirculationComponent {}
