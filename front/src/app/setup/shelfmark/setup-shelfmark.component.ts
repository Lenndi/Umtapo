import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormControl} from '@angular/forms';
import {Library} from '../../../entity/library';
import {logger} from '../../../environments/environment';
import {ShelfmarkValidator} from '../../../validator/shelfmark.validator';
import {Router} from '@angular/router';
import {Z3950Service} from '../../../service/z3950.service';
import {SetupDataService} from '../../../service/data-binding/setup-data.service';
import {Z3950} from '../../../entity/z3950';
import {Setup} from '../setup.interface';
import {ToastsManager} from 'ng2-toastr';

@Component({
  selector: 'umt-setup-shelfmark',
  templateUrl: './setup-shelfmark.component.html',
  styleUrls: ['../setup.component.scss']
})
export class SetupShelfmarkComponent implements OnInit, Setup {
  form: FormGroup;
  z3950Sources: Z3950[];
  shelfMarkNb: FormControl;
  shelfMarkNbMsg: string;
  defaultZ3950: FormControl;
  defaultZ3950Msg: string;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private z3950Service: Z3950Service,
    public dataService: SetupDataService,
    public toastr: ToastsManager,
    public vRef: ViewContainerRef
  ) {
    let library = this.dataService.library;
    this.shelfMarkNb = new FormControl(
      library != null ? library.shelfMarkNb : '',
      [Validators.required, ShelfmarkValidator.nbFields]);
    this.shelfMarkNbMsg = 'Le nombre de champs pour la cote doit être compris entre 1 et 5';
    this.defaultZ3950 = new FormControl(
      library != null ? library.defaultZ3950 : '',
      Validators.required);
    this.defaultZ3950Msg = 'Merci de sélectionner votre source ISBN favorite';
  }

  ngOnInit(): void {
    this.dataService.step = 1;
    this.dataService.title = 'Cotation';

    if (!this.dataService.library) {
      this.dataService.library = new Library();
      this.dataService.library.external = false;
    }

    this.z3950Service.findAll()
      .then(z3950Sources => this.z3950Sources = z3950Sources);

    this.form = this.formBuilder.group({
      'shelfMarkNb': this.shelfMarkNb,
      'defaultZ3950': this.defaultZ3950
    });
  }

  onSubmit(value: any): void {
    if (this.form.valid) {
      this.saveData();

      this.router.navigate(['setup/' + (this.dataService.step + 1)]);
    } else {
      logger.info('Invalid form :', value);

      if (this.form.controls['shelfMarkNb'].invalid) {
        this.toastr.error(this.shelfMarkNbMsg, 'Oops', {toastLife: 2000});
      }
      if (this.form.controls['defaultZ3950'].invalid) {
        this.toastr.error(this.defaultZ3950Msg, 'Oops', {toastLife: 2000});
      }
    }
  }

  saveData(): void {
    let value = this.form.value;
    this.dataService.library.shelfMarkNb  = value.shelfMarkNb;
    this.dataService.library.defaultZ3950 = value.defaultZ3950;
  }
}
