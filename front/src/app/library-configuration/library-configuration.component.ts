import {Component, OnInit} from '@angular/core';
import {LibraryService} from '../../service/library.service';
import {Library} from '../../entity/library';
import {logger} from '../../environments/environment';
import {FormBuilder, FormControl, Validators, FormGroup} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {SetupDataService} from '../../service/data-binding/setup-data.service';
import {VariousValidator} from '../../validator/various-validator';
import {Z3950} from '../../entity/z3950';
import {Z3950Service} from '../../service/z3950.service';

@Component({
  selector: 'umt-library-configuration',
  templateUrl: './library-configuration.component.html'
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
  libraryName: FormControl;
  libraryNameMsg: string;
  selectedLibrary = false;
  z3950Sources: Z3950[];
  defaultZ3950: FormControl;
  defaultZ3950Msg: string;

  constructor(private formBuilder: FormBuilder,
              private libraryService: LibraryService,
              private z3950Service: Z3950Service,
              public dataService: SetupDataService,
              public toastr: ToastrService) {}

  ngOnInit(): void {
    this.libraryService.findPartnerLibraries().then(libraries => {
      if (libraries.length > 0) {
        this.library = libraries[0];
        this.loadLibraryConfiguration(this.library);
        this.selectedLibrary = true;
      }
    });
    this.dataService.step = 2;
    this.dataService.title = 'Divers';
  }

  onSubmit(): void {
    if (this.form.valid) {
      this.saveData();

      if (!this.library.id) {
        this.libraryService.save(this.library)
          .then(library => {
            this.libraries.push(library);
            this.libraryService.saveLocally(library);
            this.toastr.success('La bibliothèque a été créée');
          })
          .catch(error => {
            logger.error('Library create', error);
            this.toastr.error('Erreur durant la création de la bibliothèque');
          });
      } else {
        this.libraryService.update(this.library)
          .then(library => {
            this.libraryService.saveLocally(library);
            this.toastr.success('La bibliothèque a été mise à jour');
          })
          .catch(error => {
            logger.error('Library update', error);
            this.toastr.error('Erreur durant la mise à jour de la bibliothèque');
          });
      }
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
      if (this.form.controls['libraryName'].invalid) {
        this.toastr.error(this.libraryNameMsg, 'Oops');
      }
      if (this.form.controls['defaultZ3950'].invalid) {
        this.toastr.error(this.defaultZ3950Msg, 'Oops');
      }
    }
  }

  saveData(): void {
    let value = this.form.value;
    this.library.borrowDuration = value.borrowDuration;
    this.library.subscriptionDuration = value.subscriptionDuration;
    this.library.currency = value.currency;
    this.library.name = value.libraryName;
    this.library.defaultZ3950 = value.defaultZ3950;
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
    this.libraryName = new FormControl(library != null ? library.name : '', Validators.required);
    this.libraryNameMsg = `Merci d'indiquer un nom de bibliothèque`;
    this.defaultZ3950 = new FormControl(
      library != null ? library.defaultZ3950 : '',
      Validators.required);
    this.defaultZ3950Msg = 'Merci de sélectionner votre source ISBN favorite';

    this.form = this.formBuilder.group({
      'borrowDuration': this.borrowDuration,
      'subscriptionDuration': this.subscriptionDuration,
      'currency': this.currency,
      'libraryName': this.libraryName,
      'defaultZ3950': this.defaultZ3950
    });
  }

  /*addNewLibrary() {
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
  }*/
}
