import { Component, OnInit } from '@angular/core';
import {Borrower} from '../../entity/borrower';
import {Subscription} from '../../entity/subscription';
import {BorrowerService} from '../../service/borrower.service';
import {ValidationService} from '../../service/ValidationService';
import {Validators, FormBuilder, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {LibraryService} from "../../service/library.service";
declare const Materialize: any;

@Component({
  selector: 'app-new-borrower',
  templateUrl: './new-borrower.component.html',
  styleUrls: ['./new-borrower.component.scss']
})
export class NewBorrowerComponent implements OnInit {
  private form: FormGroup;
  endSubscription: Date;
  dateToday: Date;

  constructor(
      private formBuilder: FormBuilder,
      private libraryService: LibraryService
  ) {}

  ngOnInit() {
    // this.onBirthdateChange();
    this.endSubscription = new Date();
    this.dateToday = new Date();
    this.endSubscription.setDate(this.dateToday.getDate() + 6);

    this.form = this.formBuilder.group({
      'name': ['', Validators.required],
      'email': ['', Validators.compose([Validators.required, ValidationService.emailValidator])],
      'birthdate': ['', Validators.compose([Validators.required, ValidationService.dateValidator])],
      'subscription': [this.dateToday.toDateString(), Validators.compose([Validators.required, ValidationService.dateValidator])],
      'phone': ['', Validators.required],
      'address1': ['', Validators.required],
      'address2': ['', Validators.required],
      'zip': ['', Validators.required],
      'city': ['', Validators.required],
      'quota': ['', Validators.required],
      'contribution': ['', Validators.required],
      'comment': ['', Validators.required],
      'emailOptin': ['', Validators.required],
    });
  }
}
