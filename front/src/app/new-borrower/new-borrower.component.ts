///<reference path="../../service/borrower.service.ts"/>
import {Component, OnInit} from '@angular/core';
import {Borrower} from '../../entity/borrower';
import {BorrowerService} from '../../service/borrower.service';
import {ValidationService} from '../../service/validationService';
import {Validators, FormBuilder, FormGroup} from '@angular/forms';
import {Address} from '../../entity/address';
import {logger} from '../../environments/environment';
import {MdSnackBar, MdSnackBarConfig} from '@angular/material';
import {Subscription} from '../../entity/subscription';


@Component({
  selector: 'app-new-borrower',
  templateUrl: './new-borrower.component.html',
  styleUrls: ['./new-borrower.component.scss']
})


export class NewBorrowerComponent implements OnInit {
  private form: FormGroup;
  private endSubscription: Date;
  private dateToday: Date;
  private borrower: Borrower = new Borrower();
  private address: Address = new Address();
  private subscriptionArray: Subscription[] = [];
  private subscription: Subscription = new Subscription();
  private config = new MdSnackBarConfig();


  constructor(
    private formBuilder: FormBuilder,
    private borrowerService: BorrowerService,
    private snackBar: MdSnackBar
  ) {
  }

  // ########################################################
  // ################### INTERNAL FUNCTION ##################
  // ########################################################

  /**
   *
   */
  initializeForm() {
    this.form = this.formBuilder.group({
      'name': ['', Validators.required],
      'email': ['', Validators.compose([Validators.required, ValidationService.emailValidator])],
      'birthday': ['', Validators.compose([Validators.required, ValidationService.dateValidator])],
      'startSubscription': ['', Validators.compose([Validators.required, ValidationService.dateValidator])],
      'phone': ['', Validators.required],
      'address1': ['', Validators.required],
      'address2': ['', Validators.required],
      'zip': ['', Validators.required],
      'city': ['', Validators.required],
      'quota': ['', Validators.required],
      'contribution': ['', Validators.required],
      'comment': [''],
      'emailOptin': ['']
    });
  }


  // TODO Add Real Time.
  /**
   *
   */
  addSubscriptionDurationToDate() {
    let date = new Date(this.form.controls['startSubscription'].value);
    this.endSubscription = this.addDays(date, 365);
  }

  /**
   *
   * @param date
   * @param days
   * @returns {Date}
     */
  addDays(date: Date, days: number): Date {
    console.log('adding ' + days + ' days');
    console.log(date);
    date.setDate(date.getDate() + days);
    console.log(date);
    return date;
  }

  ngOnInit() {
    this.config.duration = 1000;
    this.endSubscription = new Date();
    this.dateToday = new Date();

    this.initializeForm();

  }

  saveOk() {
    this.snackBar.open('Emprunteur crée', 'OK', this.config);
    this.initializeForm();
  }

  onSubmit(value: any): void {

    if (this.form.dirty && this.form.valid) {
      alert(`Name: ${this.form.value.name} Email: ${this.form.value.email}`);
    }

    if (this.form.valid) {

      logger.info('valid form :', value);
      this.borrower.setName(value.name);
      this.borrower.setBirthday(new Date(value.birthday));
      this.borrower.setQuota(value.quota);
      this.borrower.setEmailOptin(value.emailOptin);
      this.address.setAddress1(value.address1);
      this.address.setAddress2(value.address2);
      this.address.setZip(value.zip);
      this.address.setCity(value.city);
      this.address.setPhone(value.phone);
      this.address.setEmail(value.email);
      this.subscription.setStart(new Date(value.startSubscription));
      this.subscription.setEnd(this.endSubscription);
      this.subscription.setContribution(value.contribution);
      this.subscriptionArray = [];
      this.subscriptionArray.push(this.subscription);
      this.borrower.setAddress(this.address);
      this.borrower.setSubscriptions(this.subscriptionArray);

      this.borrowerService
        .save(this.borrower).then(borrower => this.saveOk()).catch(borrower =>
        this.snackBar.open('Problème création emprunteur', 'OK', this.config));

    } else {
      logger.info('Invalid form :', value);
      if (this.form.controls['email'].invalid) {
        logger.info('email invalid');
        logger.info(this.form.controls['email'].value);
        this.snackBar.open(ValidationService.getValidatorErrorMessage('invalidEmailAddress', true) + ' Email', 'OK',
          this.config);
      }
      if (this.form.controls['startSubscription'].invalid) {
        logger.info('startSubscription invalid');
        logger.info(this.form.controls['startSubscription'].value);
        this.snackBar.open(ValidationService.getValidatorErrorMessage('invalidDate', true) + ' StartSubscription', 'OK',
          this.config);
      }
      if (this.form.controls['birthday'].invalid) {
        logger.info('birthday invalid');
        logger.info(this.form.controls['birthday'].value);
        this.snackBar.open(ValidationService.getValidatorErrorMessage('invalidDate', true) + ' birthday', 'OK',
          this.config);
      }
    }
  }
}
