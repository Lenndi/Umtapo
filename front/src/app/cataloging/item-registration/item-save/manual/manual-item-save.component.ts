import { Component, OnInit } from '@angular/core';
import {RecordDetailFormatterService} from '../../../../../service/record-detail-formatter.service';

@Component({
  selector: 'umt-manual-item-save',
  templateUrl: './manual-item-save.component.html',
  styleUrls: ['./manual-item-save.component.scss'],
  providers: [RecordDetailFormatterService]
})
export class ManualItemSaveComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
