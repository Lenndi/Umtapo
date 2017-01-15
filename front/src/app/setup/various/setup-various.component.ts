import {Component, OnInit} from '@angular/core';
import {Library} from '../../../entity/library';
import {FormBuilder, FormGroup, Validators, FormControl} from '@angular/forms';
import {logger} from '../../../environments/environment';
import {SetupDataService} from '../../../service/data-binding/setup-data.service';
import {LibraryService} from '../../../service/library.service';
import {Router} from '@angular/router';
import {MdSnackBar} from '@angular/material';
import {VariousValidator} from '../../../validator/VariousValidator';

@Component({
  selector: 'umt-setup-various',
  templateUrl: './setup-various.component.html',
  styleUrls: ['../setup.component.scss'],
  providers: [LibraryService]
})
export class SetupVariousComponent implements OnInit {
  library: Library;
  form: FormGroup;
  borrowDuration: FormControl;
  borrowDurationMsg: string;
  subscriptionDuration: FormControl;
  subscriptionDurationMsg: string;
  currency: FormControl;
  currencyMsg: string;
  itemStartNumber: FormControl;
  itemStartNumberMsg: string;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private libraryService: LibraryService,
    public dataService: SetupDataService,
    private snackBar: MdSnackBar
  ) {
    let library = this.dataService.library;

    this.borrowDuration = new FormControl(
      library != null ? library.getBorrowDuration() : '',
      [Validators.required, VariousValidator.positive]);
    this.borrowDurationMsg = 'Merci d\'indiquer une durée d\'emprunt par défaut';
    this.subscriptionDuration = new FormControl(
      library != null ? library.getSubscriptionDuration() : '',
      [Validators.required, VariousValidator.positive]);
    this.subscriptionDurationMsg = 'Merci d\'indiquer une durée d\'abonnement par défaut';
    this.currency = new FormControl(
      library != null ? library.getCurrency() : '',
      Validators.required);
    this.currencyMsg = 'Merci d\'indiquer une monnaie';
    this.itemStartNumber = new FormControl(
      this.dataService.itemStartNumber != null ? this.dataService.itemStartNumber : '',
      [Validators.required, VariousValidator.positive]);
    this.itemStartNumberMsg = `Merci d\'indiquer un identifiant numérique à partir duquel seront créé la numérotation 
        automatique des documents`;
  }

  ngOnInit(): void {
    this.dataService.step = 2;
    this.dataService.title = 'Divers';
    this.library = this.dataService.library;

    this.form = this.formBuilder.group({
      'borrowDuration': this.borrowDuration,
      'subscriptionDuration': this.subscriptionDuration,
      'currency': this.currency,
      'itemStartNumber': this.itemStartNumber
    });
  }

  onSubmit(value: any): void {
    if (this.form.valid) {
      this.dataService.itemStartNumber = value.itemStartNumber;
      this.library.setBorrowDuration(value.borrowDuration);
      this.library.setSubscriptionDuration(value.subscriptionDuration);
      this.library.setCurrency(value.currency);

      this.libraryService.save(this.library)
        .then(library => {
          this.libraryService.saveLocally(library);
        });

      this.router.navigate(['circulation']);
    } else {
      logger.info('Invalid form :', this.form);

      if (this.form.controls['borrowDuration'].invalid) {
        this.snackBar.open(this.borrowDurationMsg, 'OK');
      }
      if (this.form.controls['subscriptionDuration'].invalid) {
        this.snackBar.open(this.subscriptionDurationMsg, 'OK');
      }
      if (this.form.controls['currency'].invalid) {
        this.snackBar.open(this.currencyMsg, 'OK');
      }
      if (this.form.controls['itemStartNumber'].invalid) {
        this.snackBar.open(this.itemStartNumberMsg, 'OK');
      }
    }
  }
}
