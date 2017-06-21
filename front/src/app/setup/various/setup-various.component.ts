import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {logger} from '../../../environments/environment';
import {SetupDataService} from '../../../service/data-binding/setup-data.service';
import {LibraryService} from '../../../service/library.service';
import {Router} from '@angular/router';
import {VariousValidator} from '../../../validator/various-validator';
import {Setup} from '../setup.interface';
import {ToastrService} from 'ngx-toastr';

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
  firstInternalId: FormControl;
  firstInternalIdMsg: string;
  libraryName: FormControl;
  libraryNameMsg: string;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private libraryService: LibraryService,
    public dataService: SetupDataService,
    public toastr: ToastrService
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
    this.currency = new FormControl(library != null ? library.currency : '', Validators.required);
    this.currencyMsg = 'Merci d\'indiquer une monnaie';
    this.firstInternalId = new FormControl(
      library != null ? library.firstInternalId : '',
      [Validators.required, VariousValidator.positive]);
    this.firstInternalIdMsg = `Merci d'indiquer un identifiant numérique à partir duquel seront créé la numérotation 
        automatique des documents`;
    this.libraryName = new FormControl(library != null ? library.name : '', Validators.required);
    this.libraryNameMsg = `Merci d'indiquer un nom de bibliothèque`;
  }

  ngOnInit(): void {
    this.dataService.step = 2;
    this.dataService.title = 'Divers';

    this.form = this.formBuilder.group({
      'borrowDuration': this.borrowDuration,
      'subscriptionDuration': this.subscriptionDuration,
      'currency': this.currency,
      'firstInternalId': this.firstInternalId,
      'libraryName': this.libraryName
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
        this.toastr.error(this.borrowDurationMsg, 'Oops');
      }
      if (this.form.controls['subscriptionDuration'].invalid) {
        this.toastr.error(this.subscriptionDurationMsg, 'Oops');
      }
      if (this.form.controls['currency'].invalid) {
        this.toastr.error(this.currencyMsg, 'Oops');
      }
      if (this.form.controls['firstInternalId'].invalid) {
        this.toastr.error(this.firstInternalIdMsg, 'Oops');
      }
      if (this.form.controls['libraryName'].invalid) {
        this.toastr.error(this.libraryNameMsg, 'Oops');
      }
    }
  }

  saveData(): void {
    let value = this.form.value;
    this.dataService.library.firstInternalId = value.firstInternalId;
    this.dataService.library.borrowDuration = value.borrowDuration;
    this.dataService.library.subscriptionDuration = value.subscriptionDuration;
    this.dataService.library.currency = value.currency;
    this.dataService.library.name = value.libraryName;
  }
}
