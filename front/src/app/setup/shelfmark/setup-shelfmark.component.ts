import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormControl} from '@angular/forms';
import {Library} from '../../../entity/library';
import {logger} from '../../../environments/environment';
import {ShelfmarkValidator} from '../../../validator/shelfmark.validator';
import {Router} from '@angular/router';
import {Z3950Service} from '../../../service/z3950.service';
import {SetupDataService} from '../../../service/data-binding/setup-data.service';
import {Z3950} from '../../../entity/z3950';
import {MdSnackBar} from '@angular/material';

@Component({
  selector: 'umt-setup-shelfmark',
  templateUrl: './setup-shelfmark.component.html',
  styleUrls: ['../setup.component.scss']
})
export class SetupShelfmarkComponent implements OnInit {
  library: Library;
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
    private snackBar: MdSnackBar
  ) {
    let library = this.dataService.library;
    this.shelfMarkNb = new FormControl(
      library != null ? library.getShelfMarkNb() : '',
      [Validators.required, ShelfmarkValidator.nbFields]);
    this.shelfMarkNbMsg = 'Le nombre de champs pour la cote doit être compris entre 1 et 5';
    this.defaultZ3950 = new FormControl(
      library != null ? library.getDefaultZ3950() : '',
      Validators.required);
    this.defaultZ3950Msg = 'Merci de sélectionner votre source ISBN favorite';
  }

  ngOnInit(): void {
    this.dataService.step = 1;
    this.dataService.title = 'Cotation';
    this.library = new Library();

    this.z3950Service.findAll()
      .then(z3950Sources => this.z3950Sources = z3950Sources);

    this.form = this.formBuilder.group({
      'shelfMarkNb': this.shelfMarkNb,
      'defaultZ3950': this.defaultZ3950
    });
  }

  onSubmit(value: any): void {
    if (this.form.valid) {
      this.library.setShelfMarkNb(value.shelfMarkNb);
      this.library.setDefaultZ3950(value.defaultZ3950);

      this.dataService.library = this.library;

      this.router.navigate(['setup/' + (this.dataService.step + 1)]);
    } else {
      logger.info('Invalid form :', value);

      if (this.form.controls['shelfMarkNb'].invalid) {
        this.snackBar.open(this.shelfMarkNbMsg, 'OK', null);
      }
      if (this.form.controls['defaultZ3950'].invalid) {
        this.snackBar.open(this.defaultZ3950Msg, 'OK', null);
      }
    }
  }
}
