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
  selector: 'app-setup-various',
  templateUrl: './setup-various.component.html',
  styleUrls: ['../setup.component.scss'],
  providers: [LibraryService]
})
export class SetupVariousComponent implements OnInit {
  private library: Library;
  private form: FormGroup;

  private borrowDuration: FormControl;
  private borrowDurationMsg: string;
  private subscriptionDuration: FormControl;
  private subscriptionDurationMsg: string;
  private currency: FormControl;
  private currencyMsg: string;
  private itemStartNumber: FormControl;
  private itemStartNumberMsg: string;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private libraryService: LibraryService,
    private setupDataService: SetupDataService,
    private snackBar: MdSnackBar
  ) {
    let library = this.setupDataService.getLibrary();

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
      this.setupDataService.getItemStartNumber() != null ? this.setupDataService.getItemStartNumber() : '',
      [Validators.required, VariousValidator.positive]);
    this.itemStartNumberMsg = `Merci d\'indiquer l\'identifiant à partir duquel seront créé la numérotation automatique
      des documents`;
  }

  ngOnInit(): void {
    this.setupDataService.setStep(2);
    this.setupDataService.setTitle('Divers');
    this.library = this.setupDataService.getLibrary();

    this.form = this.formBuilder.group({
      'borrowDuration': this.borrowDuration,
      'subscriptionDuration': this.subscriptionDuration,
      'currency': this.currency,
      'itemStartNumber': this.itemStartNumber
    });
  }

  onSubmit(value: any): void {
    if (this.form.valid) {
      this.setupDataService.setItemStartNumber(value.itemStartNumber);
      this.library.setBorrowDuration(value.borrowDuration);
      this.library.setSubscriptionDuration(value.subscriptionDuration);
      this.library.setCurrency(value.currency);

      this.libraryService.save(this.library)
        .then(library => {
          this.libraryService.saveLocally(library);
        });

      this.router.navigate(['circulation']);
    } else {
      logger.info('Invalid form :', value);

      if (this.form.controls['borrowDuration'].invalid) {
        this.snackBar.open(this.borrowDurationMsg, 'OK', null);
      }
      if (this.form.controls['subscriptionDuration'].invalid) {
        this.snackBar.open(this.subscriptionDurationMsg, 'OK', null);
      }
      if (this.form.controls['currency'].invalid) {
        this.snackBar.open(this.currencyMsg, 'OK', null);
      }
      if (this.form.controls['itemStartNumber'].invalid) {
        this.snackBar.open(this.itemStartNumberMsg, 'OK', null);
      }
    }
  }
}
