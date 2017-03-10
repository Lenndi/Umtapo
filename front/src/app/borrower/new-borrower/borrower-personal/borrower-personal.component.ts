import { Component, OnInit } from '@angular/core';
import {NewBorrowerDataService} from '../../../../service/data-binding/new-borrower-data.service';
import {FormControl, Validators, FormGroup, FormBuilder} from '@angular/forms';
import {ValidationService} from '../../../../validator/validationService';
import {logger} from 'codelyzer/util/logger';
import {Address} from '../../../../entity/address';
import {MdSnackBar} from '@angular/material';
import {Borrower} from '../../../../entity/borrower';
import {Router} from '@angular/router';
import {NewBorrower} from '../new-borrower.interface';

@Component({
  selector: 'umt-borrower-personal',
  templateUrl: './borrower-personal.component.html',
  styleUrls: ['./borrower-personal.component.scss']
})
export class BorrowerPersonalComponent implements OnInit, NewBorrower {
  form: FormGroup;
  name: FormControl;
  email: FormControl;
  birthday: FormControl;
  phone: FormControl;
  address1: FormControl;
  address2: FormControl;
  zip: FormControl;
  city: FormControl;

  constructor(
    public dataService: NewBorrowerDataService,
    private formBuilder: FormBuilder,
    private snackBar: MdSnackBar,
    private router: Router
  ) {
    let borrower: Borrower = this.dataService.borrower;
    this.name = new FormControl(
      borrower != null ? borrower.name : '',
      Validators.required);
    this.email = new FormControl(
      borrower != null ? borrower.address.email : '',
      [Validators.required, ValidationService.emailValidator]);
    this.birthday = new FormControl(
      borrower != null ? borrower.birthday.toJSON().split('T')[0] : '',
      [Validators.required, ValidationService.dateValidator]);
    this.phone = new FormControl(
      borrower != null ? borrower.address.phone : '',
      Validators.required);
    this.address1 = new FormControl(
      borrower != null ? borrower.address.address1 : '',
      Validators.required);
    this.address2 = new FormControl(borrower != null ? borrower.address.address2 : '');
    this.zip = new FormControl(
      borrower != null ? borrower.address.zip : '',
      Validators.required);
    this.city = new FormControl(
      borrower != null ? borrower.address.city : '',
      Validators.required);
    this.dataService.step = 1;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      'name': this.name,
      'email': this.email,
      'birthday': this.birthday,
      'phone': this.phone,
      'address1': this.address1,
      'address2': this.address2,
      'zip': this.zip,
      'city': this.city
    });
  }

  onSubmit(value: any): void {

    if (this.form.valid) {
      logger.info('valid form :', value);

      this.saveData();

      this.router.navigate(['borrowers/new/' + (this.dataService.step + 1)]);
    } else {
      logger.info('Invalid form :', value);
      if (this.form.controls['email'].invalid) {
        this.snackBar.open(ValidationService.getValidatorErrorMessage('invalidEmailAddress', true) + ' Email', 'OK');
      }
      if (this.form.controls['birthday'].invalid) {
        this.snackBar.open(ValidationService.getValidatorErrorMessage('invalidDate', true) + ' birthday', 'OK');
      }
    }
  }

  saveData(): void {
    let value = this.form.value;

    if (!this.dataService.borrower) {
      this.dataService.borrower = new Borrower();
    }
    this.dataService.borrower.name = value.name;
    this.dataService.borrower.birthday = new Date(value.birthday);
    this.dataService.borrower.emailOptin = value.emailOptin;

    if (!this.dataService.borrower.address) {
      this.dataService.borrower.address = new Address();
    }

    this.dataService.borrower.address.address1 = value.address1;
    this.dataService.borrower.address.address2 = value.address2;
    this.dataService.borrower.address.zip = value.zip;
    this.dataService.borrower.address.city = value.city;
    this.dataService.borrower.address.phone = value.phone;
    this.dataService.borrower.address.email = value.email;
  }
}
