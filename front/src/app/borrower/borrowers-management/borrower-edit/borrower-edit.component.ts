import {Component, OnInit, ViewChild, OnDestroy, ViewContainerRef} from '@angular/core';
import {BorrowerService} from '../../../../service/borrower.service';
import {ModalDirective} from 'ng2-bootstrap';
import {FormGroup, FormControl, FormBuilder, Validators, Validator} from '@angular/forms';
import {BorrowerDataService} from '../../../../service/data-binding/borrower-data.service';
import {Subscription} from 'rxjs';
import {Borrower} from '../../../../entity/borrower';
import {ValidationService} from '../../../../validator/validationService';
import {logger} from 'codelyzer/util/logger';
import {ToastsManager} from 'ng2-toastr';

@Component({
  selector: 'umt-borrower-edit',
  templateUrl: 'borrower-edit.component.html',
  styleUrls: ['borrower-edit.component.scss']
})
export class BorrowerEditComponent implements OnDestroy {

  @ViewChild('borrowerEditModal') public borrowerEditModal: ModalDirective;
  borrowerForm: FormGroup;
  name: FormControl;
  birthday: FormControl;
  email: FormControl;
  emailOptin: FormControl;
  phone: FormControl;
  address1: FormControl;
  address2: FormControl;
  zip: FormControl;
  city: FormControl;
  quota: FormControl;
  borrowerSubscription: Subscription;
  selectedBorrower: Borrower;

  constructor(
      private formBuilder: FormBuilder,
      private borrowerService: BorrowerService,
      private dataService: BorrowerDataService,
      public toastr: ToastsManager,
      public vRef: ViewContainerRef
  ) {
    this.borrowerSubscription = this.dataService.selectedBorrower$.subscribe(
      borrower => {
        this.selectedBorrower = borrower;
        this.populateForm();
        this.borrowerEditModal.show();
      }
    );
  }

  populateForm(): void {
    this.name = new FormControl(this.selectedBorrower.name, Validators.required);
    this.birthday = new FormControl(this.selectedBorrower.birthday.toString().split('T')[0], Validators.required);
    this.email = new FormControl(this.selectedBorrower.address.email,
      [Validators.required, ValidationService.emailValidator]);
    this.phone = new FormControl(this.selectedBorrower.address.phone, Validators.required);
    this.address1 = new FormControl(this.selectedBorrower.address.address1, Validators.required);
    this.address2 = new FormControl(this.selectedBorrower.address.address2);
    this.zip = new FormControl(this.selectedBorrower.address.zip, Validators.required);
    this.city = new FormControl(this.selectedBorrower.address.city, Validators.required);
    this.quota = new FormControl(this.selectedBorrower.quota, Validators.required);
    this.emailOptin = new FormControl(this.selectedBorrower.emailOptin);

    if (!this.borrowerForm) {
      this.borrowerForm = this.formBuilder.group({
        'name': this.name,
        'birthday': this.birthday,
        'email': this.email,
        'phone': this.phone,
        'address1': this.address1,
        'address2': this.address2,
        'zip': this.zip,
        'city': this.city,
        'quota': this.quota,
        'emailOptin': this.emailOptin
      });
    }
  }

  onCancel(): void {
    this.borrowerEditModal.hide();
  }

  onSubmit(value: any): void {
    if (this.borrowerForm.valid) {
      logger.info('valid form :', value);

      this.saveData(value);

      this.borrowerService
        .update(this.selectedBorrower)
        .then(borrower => {
          this.borrowerEditModal.hide();
          this.dataService.notifyUpdatedBorrower(borrower);
          // TODO: this.toastr.info(`L'usager a été mis à jour`, 'OK', {toastLife: 2000});
        })
        .catch(response => {
          logger.error(response);
          this.borrowerEditModal.hide();
          this.toastr.error(`Problème durant la mise à jour de l'usager`, 'Problème', {toastLife: 2000});
        });
    } else {
      logger.info('Invalid form :', value);
      this.toastr.error(`Le formulaire n'est pas valide`, 'Erreur', {toastLife: 2000});
    }
  }

  saveData(value: any) {
    this.selectedBorrower.name = value.name;
    this.selectedBorrower.birthday = new Date(value.birthday);
    this.selectedBorrower.address.email = value.email;
    this.selectedBorrower.address.phone = value.phone;
    this.selectedBorrower.address.address1 = value.address1;
    this.selectedBorrower.address.address2 = value.address2;
    this.selectedBorrower.address.zip = value.zip;
    this.selectedBorrower.address.city = value.city;
    this.selectedBorrower.quota = value.quota;
    this.selectedBorrower.emailOptin = value.emailOptin;
  }

  ngOnDestroy() {
    this.borrowerSubscription.unsubscribe();
  }
}
