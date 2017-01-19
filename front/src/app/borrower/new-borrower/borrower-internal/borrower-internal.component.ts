import { Component, OnInit } from '@angular/core';
import {NewBorrowerDataService} from '../../../../service/data-binding/new-borrower-data.service';
import {FormGroup, FormControl, FormBuilder, Validators} from '@angular/forms';
import {MdSnackBar} from '@angular/material';
import {ValidationService} from '../../../../validator/validationService';
import {logger} from 'codelyzer/util/logger';
import {Subscription} from '../../../../entity/subscription';
import {Library} from '../../../../entity/library';
import {LibraryService} from '../../../../service/library.service';
import {BorrowerService} from '../../../../service/borrower.service';
import {Borrower} from '../../../../entity/borrower';

@Component({
  selector: 'umt-borrower-internal',
  templateUrl: './borrower-internal.component.html',
  styleUrls: ['./borrower-internal.component.scss'],
  providers: [BorrowerService]
})
export class BorrowerInternalComponent implements OnInit {
  form: FormGroup;
  library: Library;
  startSubscription: FormControl;
  endSubscription: Date;
  quota: FormControl;
  contribution: FormControl;
  comment: FormControl;
  emailOptin: FormControl;

  constructor(
    public dataService: NewBorrowerDataService,
    private formBuilder: FormBuilder,
    private snackBar: MdSnackBar,
    private libraryService: LibraryService,
    private borrowerService: BorrowerService
  ) {
    let borrower: Borrower = this.dataService.borrower;
    let subscription: Subscription;
    if (borrower != null && borrower.subscriptions != null) {
      subscription = borrower.subscriptions[0];
    }
    this.startSubscription = new FormControl(
      subscription != null ? subscription.start : '',
      [Validators.required, ValidationService.dateValidator]);
    this.quota = new FormControl(borrower != null ? borrower.quota : '', Validators.required);
    this.contribution = new FormControl(subscription != null ? subscription.contribution : '', Validators.required);
    this.comment = new FormControl(borrower != null ? borrower.comment : '');
    this.emailOptin = new FormControl(borrower != null ? borrower.emailOptin : '');

    this.library = this.libraryService.findLocally();
    this.dataService.step = 2;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      'startSubscription': this.startSubscription,
      'quota': this.quota,
      'contribution': this.contribution,
      'comment': this.comment,
      'emailOptin': this.emailOptin
    });
  }

  onSubmit(value: any): void {

    if (this.form.valid) {
      logger.info('valid form :', value);
      this.dataService.borrower.quota = value.quota;
      this.dataService.borrower.emailOptin = value.emailOptin;

      let subscription: Subscription = new Subscription();
      subscription.start = new Date(value.startSubscription);
      this.setEndSubscriptionDate();
      subscription.end = this.endSubscription;
      subscription.contribution = value.contribution;

      this.dataService.borrower.subscriptions.push(subscription);

      this.borrowerService
        .save(this.dataService.borrower)
        .then(borrower => this.snackBar.open('Emprunteur crée', 'OK'))
        .catch(borrower => this.snackBar.open('Problème création emprunteur', 'OK'));
    } else {
      logger.info('Invalid form :', value);
      if (this.form.controls['startSubscription'].invalid) {
        this.snackBar.open(
          ValidationService.getValidatorErrorMessage('invalidDate', true) + ' StartSubscription',
          'OK');
      }
    }
  }

  setEndSubscriptionDate() {
    let date = new Date(this.startSubscription.value);
    date.setDate(date.getDate() + this.library.subscriptionDuration);

    this.endSubscription = date;
  }
}
