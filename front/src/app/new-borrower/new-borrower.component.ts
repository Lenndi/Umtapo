///<reference path="../../service/borrower.service.ts"/>
import {Component, OnInit} from '@angular/core';
import {Borrower} from '../../entity/borrower';
import {Subscription} from '../../entity/subscription';
import {BorrowerService} from '../../service/borrower.service';
import {ValidationService} from '../../service/ValidationService';
import {Validators, FormBuilder, FormGroup, FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {LibraryService} from "../../service/library.service";
import {logger} from '../../environments/environment';
import {Address} from "../../entity/address";
import {Loan} from "../../entity/loan";
import {Library} from "../../entity/library";
declare const Materialize: any;

@Component({
  selector: 'app-new-borrower',
  templateUrl: './new-borrower.component.html',
  styleUrls: ['./new-borrower.component.scss']
})
export class NewBorrowerComponent implements OnInit {
  private form: FormGroup;
  private endSubscription: Date;
  private dateToday: Date;
  private borrower: Borrower;
  private address: Address;
  private subscription: Subscription;
  private loan: Loan;
  private library: Library;

  constructor(
      private formBuilder: FormBuilder,
      private borrowerService: BorrowerService
  ) {
  }

  ngOnInit() {
    this.dateToday = new Date();
    this.endSubscription = new Date();

    this.form = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', Validators.compose([Validators.required, ValidationService.emailValidator])],
      birthdate: ['', Validators.compose([Validators.required, ValidationService.dateValidator])],
      startSubscription: [this.dateToday.toDateString(), Validators.compose([Validators.required, ValidationService.dateValidator])],
      phone: ['', Validators.required],
      address1: ['', Validators.required],
      address2: ['', Validators.required],
      zip: ['', Validators.required],
      city: ['', Validators.required],
      quota: ['', Validators.required],
      contribution: ['', Validators.required],
      comment: ['', Validators.required],
      emailOptin: ['', Validators.required],
    });
    this.endSubscription.setDate(this.dateToday.getDate() + 6);
  }
  onSubmit(value: any): void {
    if (this.form.valid) {
      this.borrower.setName(value.name);
      this.borrower.setBirthday(value.birthday);
      this.borrower.setQuota(value.quota);
      this.borrower.setEmailOptin(value.emailOptin);
      this.address.setAddress1(value.address1);
      this.address.setAddress2(value.address2);
      this.address.setZip(value.zip);
      this.address.setCity(value.city);
      this.address.setPhone(value.phone);
      this.address.setEmail(value.email);
      this.subscription.setStart(value.startSubscription);
      this.subscription.setEnd(this.endSubscription);
      this.subscription.setContribution(value.contribution);
      this.borrower.setAddress(this.address);
      this.borrower.setSubscription(this.subscription);

      logger.info("azezae");

      this.borrowerService.save(this.borrower);
    //
    //
    //   this.router.navigate(['setup/' + (this.setupDataService.getStep() + 1)]);
    // } else {
    //   logger.info('Invalid form :', value);
    //
    //   if (value.shelfMarkNb === '') {
    //     Materialize.toast('Number of fields is empty', 4000);
    //   } else if (!this.form.controls['shelfMarkNb'].valid) {
    //     Materialize.toast('Number of fields must be between 1 and 5', 4000);
    //   }
    //   if (value.defaultZ3950 === '') {
    //     Materialize.toast('Please select a favorite ISBN source', 4000);
    //   }
    }
  }
}
