import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {NewBorrowerDataService} from '../../../../service/data-binding/new-borrower-data.service';
import {FormGroup, FormControl, FormBuilder, Validators} from '@angular/forms';
import {ValidationService} from '../../../../validator/validationService';
import {Subscription} from '../../../../entity/subscription';
import {Library} from '../../../../entity/library';
import {LibraryService} from '../../../../service/library.service';
import {BorrowerService} from '../../../../service/borrower.service';
import {Borrower} from '../../../../entity/borrower';
import {SubscriptionService} from '../../../../service/subscription.service';
import {logger} from '../../../../environments/environment';
import {ToastsManager} from 'ng2-toastr';

@Component({
  selector: 'umt-borrower-internal',
  templateUrl: './borrower-internal.component.html',
  styleUrls: ['./borrower-internal.component.scss'],
  providers: [BorrowerService, SubscriptionService]
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
    public toastr: ToastsManager,
    public vRef: ViewContainerRef,
    private libraryService: LibraryService,
    private borrowerService: BorrowerService,
    private subscriptionService: SubscriptionService
  ) {
    let borrower: Borrower = this.dataService.borrower;
    if (!this.dataService.subscription) {
      this.dataService.subscription = new Subscription();
    }
    let subscription: Subscription = this.dataService.subscription;

    this.startSubscription = new FormControl(
      subscription != null ? subscription.start : new Date().toJSON().split('T')[0],
      [Validators.required, ValidationService.dateValidator]);
    this.contribution = new FormControl(
      subscription.contribution != null ? subscription.contribution : '',
      Validators.required);
    this.quota = new FormControl(borrower != null ? borrower.quota : '', Validators.required);
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
      this.dataService.borrower.comment = value.comment;

      this.dataService.subscription.start = new Date(value.startSubscription);
      this.dataService.subscription.contribution = value.contribution;
      this.dataService.subscription.library = this.library;

      this.borrowerService
        .save(this.dataService.borrower)
        .then(borrower => {
          this.dataService.subscription.borrower = borrower;
          this.subscriptionService
            .save(this.dataService.subscription)
            .then(subscription => this.toastr.error('Usager créé', 'Oops', {toastLife: 2000}))
            .catch(response => {
              logger.error(response);
              this.toastr.error(
                'Usager créé, problème durant l\'enregistrement de l\'abonnement', 'Oops',
                {toastLife: 2000}
              );
            });
        })
        .catch(response => {
          this.toastr.error(
            'Problème durant la création de l\'usager', 'Oops',
            {toastLife: 2000}
          );
          logger.error(response);
        });
    } else {
      logger.info('Invalid form :', value);
      if (this.form.controls['startSubscription'].invalid) {
        this.toastr.error(
          ValidationService.getValidatorErrorMessage('invalidDate', true) + ' StartSubscription', 'Oops',
          {toastLife: 2000}
          );
      }
    }
  }

  /**
   * Define subscription end date based on start date and library default subscription duration.
   */
  setEndSubscriptionDate() {
    let date = new Date(this.startSubscription.value);
    date.setDate(date.getDate() + this.library.subscriptionDuration);

    this.endSubscription = date;
  }
}
