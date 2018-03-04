import {Component, OnDestroy, ViewChild} from '@angular/core';
import {ModalDirective} from 'ngx-bootstrap';
import {Borrower} from '../../../../entity/borrower';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {BorrowerDataService} from '../../../../service/data-binding/borrower-data.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs/Subscription';
import {logger} from '../../../../environments/environment';
import {SubscriptionService} from '../../../../service/subscription.service';
import {Subscription as UmtSubscription} from '../../../../entity/subscription';
import {ValidationService} from '../../../../validator/validationService';
import {LibraryService} from '../../../../service/library.service';

@Component({
  selector: 'umt-borrower-renewal',
  templateUrl: './borrower-renewal.component.html',
  providers: [SubscriptionService]
})
export class BorrowerRenewalComponent implements OnDestroy {

  @ViewChild('borrowerRenewalModal') public borrowerRenewalModal: ModalDirective;
  renewalForm: FormGroup;
  borrowerSubscription: Subscription;
  subscriptionStart: FormControl;
  contribution: FormControl;
  selectedBorrower: Borrower;
  subscription: UmtSubscription;

  constructor(
    private formBuilder: FormBuilder,
    private subscriptionService: SubscriptionService,
    public libraryService: LibraryService,
    public dataService: BorrowerDataService,
    public toastr: ToastrService
  ) {
    this.borrowerSubscription = this.dataService.selectedBorrower$.subscribe(
      borrower => {
        this.selectedBorrower = borrower;
        this.createForm();
      }
    );
  }

  onRenewal(value: any): void {
    if (this.renewalForm.valid) {
      logger.info('valid form :', value);

      this.subscription = new UmtSubscription();
      this.subscription.library = this.libraryService.findLocally();
      this.subscription.borrower = this.selectedBorrower;
      this.subscription.start = new Date(value.subscriptionStart);
      this.subscription.contribution = value.contribution;

      this.subscriptionService
        .save(this.subscription)
        .then(() => {
          this.hideModal();
          this.toastr.info(`Nouvel abonnement enregistré`, 'OK');
        })
        .catch(response => {
          logger.error(response);
          this.hideModal();
          this.toastr.error(`Problème durant l'enregistrement de l'abonnement`, 'Oops');
        });
    } else {
      logger.info('Invalid form :', value);
      this.toastr.error(`Le formulaire n'est pas valide`, 'Erreur');
    }
  }

  public hideModal(): void {
    this.borrowerRenewalModal.hide();
  }

  public onHidden(): void {
    this.dataService.isEditShown = false;
  }

  private createForm(): void {
    this.subscriptionStart = new FormControl('', [Validators.required, ValidationService.dateValidator]);
    this.contribution = new FormControl('', Validators.required);

    if (!this.renewalForm) {
      this.renewalForm = this.formBuilder.group({
        'subscriptionStart': this.subscriptionStart,
        'contribution': this.contribution
      });
    }
  }

  ngOnDestroy() {
    this.borrowerSubscription.unsubscribe();
  }
}
