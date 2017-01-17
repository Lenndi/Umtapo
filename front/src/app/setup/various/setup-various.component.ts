import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormControl} from '@angular/forms';
import {logger} from '../../../environments/environment';
import {SetupDataService} from '../../../service/data-binding/setup-data.service';
import {LibraryService} from '../../../service/library.service';
import {Router} from '@angular/router';
import {MdSnackBar} from '@angular/material';
import {VariousValidator} from '../../../validator/various-validator';
import {Setup} from '../setup.interface';

@Component({
  selector: 'umt-setup-various',
  templateUrl: './setup-various.component.html',
  styleUrls: ['../setup.component.scss'],
  providers: [LibraryService]
})
export class SetupVariousComponent implements OnInit, Setup {
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
      library != null ? library.borrowDuration : '',
      [Validators.required, VariousValidator.positive]);
    this.borrowDurationMsg = 'Merci d\'indiquer une durée d\'emprunt par défaut';
    this.subscriptionDuration = new FormControl(
      library != null ? library.subscriptionDuration : '',
      [Validators.required, VariousValidator.positive]);
    this.subscriptionDurationMsg = 'Merci d\'indiquer une durée d\'abonnement par défaut';
    this.currency = new FormControl(
      library != null ? library.currency : '',
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

    this.form = this.formBuilder.group({
      'borrowDuration': this.borrowDuration,
      'subscriptionDuration': this.subscriptionDuration,
      'currency': this.currency,
      'itemStartNumber': this.itemStartNumber
    });
  }

  onSubmit(value: any): void {
    if (this.form.valid) {
      this.saveData();

      this.libraryService.save(this.dataService.library)
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

  saveData(): void {
    let value = this.form.value;
    this.dataService.itemStartNumber = value.itemStartNumber;
    this.dataService.library.borrowDuration = value.borrowDuration;
    this.dataService.library.subscriptionDuration = value.subscriptionDuration;
    this.dataService.library.currency = value.currency;
  }
}
