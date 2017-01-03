import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {BorrowerService} from '../../service/borrower.service';
import {Borrower} from '../../entity/borrower';
import {logger} from '../../environments/environment';

@Component({
  selector: 'app-circulation-borrower-selection',
  templateUrl: './circulation-borrower-selection.component.html',
  styleUrls: ['./circulation-borrower-selection.component.scss']
})
export class CirculationBorrowerSelectionComponent implements OnInit {
  private form: FormGroup;
  private borrowerNames: FormControl;
  private idBorrower: FormControl;
  private borrowers: Borrower[];

  constructor(
    private formBuilder: FormBuilder,
    private borrowerService: BorrowerService,
    private router: Router
  ) {
    this.idBorrower =  new FormControl('');
    this.borrowerNames = new FormControl('');
  }

  ngOnInit() {
    this.borrowerService.findAll()
      .then(borrowers => this.borrowers = borrowers);


    this.form = this.formBuilder.group({
      'idBorrower': this.idBorrower,
      'borrowerNames': this.borrowerNames
    });
  }

}
