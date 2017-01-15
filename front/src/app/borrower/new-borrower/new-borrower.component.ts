import {Component, OnInit} from '@angular/core';
import {Validators, FormBuilder, FormGroup, FormControl} from '@angular/forms';
import {MdSnackBar, MdSnackBarConfig} from '@angular/material';
import {Borrower} from '../../../entity/borrower';
import {Library} from '../../../entity/library';
import {Address} from '../../../entity/address';
import {Subscription} from '../../../entity/subscription';
import {BorrowerService} from '../../../service/borrower.service';
import {LibraryService} from '../../../service/library.service';
import {logger} from '../../../environments/environment';
import {ValidationService} from '../../../validator/validationService';

@Component({
  selector: 'app-new-borrower',
  templateUrl: 'new-borrower.component.html',
  styleUrls: ['new-borrower.component.scss']
})

export class NewBorrowerComponent implements OnInit {
  form: FormGroup;
  endSubscription: Date;
  dateToday: Date;
  borrower: Borrower;
  library: Library;
  address: Address;
  subscriptionArray: Subscription[];
  subscription: Subscription;
  config;
  name: FormControl;
  email: FormControl;
  birthday: FormControl;
  phone: FormControl;
  address1: FormControl;
  address2: FormControl;
  zip: FormControl;
  city: FormControl;
  startSubscription: FormControl;
  quota: FormControl;
  contribution: FormControl;
  comment: FormControl;
  emailOptin: FormControl;

  constructor(
    private formBuilder: FormBuilder,
    private borrowerService: BorrowerService,
    private libraryService: LibraryService,
    private snackBar: MdSnackBar
  ) {
    this.borrower = new Borrower();
    this.address = new Address();
    this.subscriptionArray = [];
    this.subscription = new Subscription();
    this.config = new MdSnackBarConfig();
    this.endSubscription = new Date();
    this.dateToday = new Date();
    this.initializeFormControl();
  }

  ngOnInit() {
    this.config.duration = 1000;
    this.initializeForm();
    this.library = this.libraryService.findLocally();
  }

  onSubmit(value: any): void {

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
      this.subscriptionArray.push(this.subscription);
      this.borrower.setAddress(this.address);
      this.borrower.setSubscriptions(this.subscriptionArray);

      this.borrowerService
        .save(this.borrower)
        .then(borrower => this.saveBorrowerOk())
        .catch(borrower => this.saveBorrowerError());

    } else {
      logger.info('Invalid form :', value);
      if (this.form.controls['email'].invalid) {
        this.snackBar.open(ValidationService.getValidatorErrorMessage('invalidEmailAddress', true) + ' Email', 'OK',
          this.config);
      }
      if (this.form.controls['startSubscription'].invalid) {
        this.snackBar.open(ValidationService.getValidatorErrorMessage('invalidDate', true) + ' StartSubscription', 'OK',
          this.config);
      }
      if (this.form.controls['birthday'].invalid) {
        this.snackBar.open(ValidationService.getValidatorErrorMessage('invalidDate', true) + ' birthday', 'OK',
          this.config);
      }
    }
  }

  // #########################################################################################################
  // ########################################### INTERNAL FUNCTION ###########################################
  // #########################################################################################################

  /**
   * Initialize Borrower Save Form.
   */
  private initializeForm() {
    this.form = this.formBuilder.group({
      'name': this.name,
      'email': this.email,
      'birthday': this.birthday,
      'startSubscription': this.startSubscription,
      'phone': this.phone,
      'address1': this.address1,
      'address2': this.address2,
      'zip': this.zip,
      'city': this.city,
      'quota': this.quota,
      'contribution': this.contribution,
      'comment': this.comment,
      'emailOptin': this.emailOptin
    });
  }

  private initializeFormControl() {
    this.name = new FormControl('', Validators.required);
    this.email = new FormControl('', [Validators.required, ValidationService.emailValidator]);
    this.birthday = new FormControl('', [Validators.required, ValidationService.dateValidator]);
    this.startSubscription = new FormControl('', [Validators.required, ValidationService.dateValidator]);
    this.phone = new FormControl('', Validators.required);
    this.address1 = new FormControl('', Validators.required);
    this.address2 = new FormControl('', Validators.required);
    this.zip = new FormControl('', Validators.required);
    this.city = new FormControl('', Validators.required);
    this.quota = new FormControl('', Validators.required);
    this.contribution = new FormControl('', Validators.required);
    this.comment = new FormControl('');
    this.emailOptin = new FormControl('');
  }

  /**
   * Add subscription duration to start subscrition date.
   */
  addSubscriptionDurationToDate() {
    let date = new Date(this.form.controls['startSubscription'].value);
    this.endSubscription = this.addDays(date, this.library.getSubscriptionDuration());
  }

  /**
   *
   * @param date
   * @param days
   * @returns {Date}
   */
  private addDays(date: Date, days: number): Date {
    date.setDate(date.getDate() + days);
    return date;
  }

  /**
   * If Save Borrower Promise is successful .
   */
  saveBorrowerOk() {
    this.snackBar.open('Emprunteur crée', 'OK', this.config);
    this.initializeForm();
  }

  /**
   * If Save Borrower Promise is not successful .
   */
  saveBorrowerError() {
    this.snackBar.open('Problème création emprunteur', 'OK', this.config);
  }
}
