import {Component, OnInit} from '@angular/core';
import {LibraryService} from '../../service/library.service';
import {Library} from '../../entity/library';
import {logger} from '../../environments/environment';
import {FormBuilder, FormControl, Validators, FormGroup} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {SetupDataService} from '../../service/data-binding/setup-data.service';
import {Router} from '@angular/router';
import {VariousValidator} from '../../validator/various-validator';
import {Z3950} from '../../entity/z3950';
import {Z3950Service} from '../../service/z3950.service';
import {ShelfmarkValidator} from '../../validator/shelfmark.validator';

@Component({
  selector: 'umt-library-configuration',
  templateUrl: './library-configuration.component.html',
  styleUrls: ['./library-configuration.component.scss']
})
export class LibraryConfigurationComponent implements OnInit {

  library: Library = new Library;
  libraries: Library[] = [];
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
  selectedLibrary = false;
  newLibrary = false;
  z3950Sources: Z3950[];
  shelfMarkNb: FormControl;
  shelfMarkNbMsg: string;
  defaultZ3950: FormControl;
  defaultZ3950Msg: string;

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private libraryService: LibraryService,
              private z3950Service: Z3950Service,
              public dataService: SetupDataService,
              public toastr: ToastrService) {

  }

  ngOnInit(): void {
    this.libraryService.findPartnerLibraries().then(libraries => this.libraries = libraries);
    this.dataService.step = 2;
    this.dataService.title = 'Divers';
  }

  onSubmit(value: any): void {
    if (this.form.valid) {
      this.saveData();

      if (this.library.id == null) {
        this.newLibrary = true;
      }

      this.libraryService.save(this.library)
        .then(library => {
          if (this.newLibrary) {
            this.libraries.push(library);
          }
          this.newLibrary = false;
          this.libraryService.saveLocally(library);
        });
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
      if (this.form.controls['shelfMarkNb'].invalid) {
        this.toastr.error(this.shelfMarkNbMsg, 'Oops');
      }
      if (this.form.controls['defaultZ3950'].invalid) {
        this.toastr.error(this.defaultZ3950Msg, 'Oops');
      }
    }
  }

  saveData(): void {
    let value = this.form.value;
    this.library.firstInternalId = value.firstInternalId;
    this.library.borrowDuration = value.borrowDuration;
    this.library.subscriptionDuration = value.subscriptionDuration;
    this.library.currency = value.currency;
    this.library.name = value.libraryName;
    this.library.defaultZ3950 = value.defaultZ3950;
    this.library.shelfMarkNb = value.shelfMarkNb;
  }

  loadLibraryConfiguration(library: Library) {
    this.z3950Service.findAll()
      .then(z3950Sources => this.z3950Sources = z3950Sources);
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
    this.shelfMarkNb = new FormControl(
      library != null ? library.shelfMarkNb : '',
      [Validators.required, ShelfmarkValidator.nbFields]);
    this.shelfMarkNbMsg = 'Le nombre de champs pour la cote doit être compris entre 1 et 5';
    this.defaultZ3950 = new FormControl(
      library != null ? library.defaultZ3950 : '',
      Validators.required);
    this.defaultZ3950Msg = 'Merci de sélectionner votre source ISBN favorite';

    this.form = this.formBuilder.group({
      'borrowDuration': this.borrowDuration,
      'subscriptionDuration': this.subscriptionDuration,
      'currency': this.currency,
      'firstInternalId': this.firstInternalId,
      'libraryName': this.libraryName,
      'shelfMarkNb': this.shelfMarkNb,
      'defaultZ3950': this.defaultZ3950
    });
  }

  addNewLibrary() {
    this.library = new Library();
    this.loadLibraryConfiguration(null);
    this.selectedLibrary = true;
  }

  onChange(index) {

    if (index === 0) {
      this.selectedLibrary = false;
      this.library = null;
    } else {
      this.library = this.libraries[index - 1];
      this.loadLibraryConfiguration(this.library);
      this.selectedLibrary = true;
    }
  }
}
