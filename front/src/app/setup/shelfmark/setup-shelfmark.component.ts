import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Library} from '../../../entity/library';
import {logger} from '../../../environments/environment';
import {ShelfmarkValidator} from '../../../validator/shelfmark.validator';
import {Router} from '@angular/router';
import {Z3950Service} from '../../../service/z3950.service';
import {SetupDataService} from '../../../service/data-binding/setup-data.service';
declare const Materialize: any;

@Component({
  selector: 'app-setup-shelfmark',
  templateUrl: 'setup-shelfmark.component.html',
  styleUrls: ['../setup.component.scss']
})
export class SetupShelfmarkComponent implements OnInit {
  private library: Library;
  private form: FormGroup;
  private z3950Sources: any;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private z3950Service: Z3950Service,
    private setupDataService: SetupDataService
  ) {}

  ngOnInit(): void {
    this.setupDataService.setStep(1);
    this.library = new Library();

    this.z3950Service.findAll()
      .then(z3950Sources => this.z3950Sources = z3950Sources);

    this.form = this.formBuilder.group({
      'shelfMarkNb': ['', Validators.compose([Validators.required, ShelfmarkValidator.nbFields])],
      'defaultZ3950': ['', Validators.required],
      'useDeweyClassification': [false, Validators.required]
    });
  }

  onSubmit(value: any): void {
    if (this.form.valid) {
      this.library.setShelfMarkNb(value.shelfMarkNb);
      this.library.setDefaultZ3950(value.defaultZ3950);
      this.library.setUseDeweyClassification(value.useDeweyClassification);

      this.setupDataService.setLibrary(this.library);

      this.router.navigate(['setup/2']);
    } else {
      logger.info('Invalid form :', value);

      if (value.shelfMarkNb == '') {
        Materialize.toast('Number of fields is empty', 4000);
      } else if (!this.form.controls['shelfMarkNb'].valid) {
        Materialize.toast('Number of fields must be between 1 and 5', 4000);
      }
      if (value.defaultZ3950 == '') {
        Materialize.toast('Please select a favorite ISBN source', 4000);
      }
    }
  }
}
