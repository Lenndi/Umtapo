import { Component, OnInit } from '@angular/core';
import {BorrowerService} from '../../../service/borrower.service';
import {BorrowerDataService} from '../../../service/data-binding/borrower-data.service';
import {Borrower} from '../../../entity/borrower';

@Component({
  selector: 'umt-borrowers-management',
  templateUrl: './borrowers-management.component.html',
  styleUrls: ['./borrowers-management.component.scss'],
  providers: [BorrowerService, BorrowerDataService]
})
export class BorrowersManagementComponent {
}
