import {Component, OnInit} from '@angular/core';
import {Library} from '../../../entity/library';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {logger} from '../../../environments/environment';
import {SetupDataService} from '../../../service/data-binding/setup-data.service';
import {LibraryService} from '../../../service/library.service';
import {Router} from '@angular/router';
declare const Materialize: any;

@Component({
  selector: 'app-setup-various',
  templateUrl: './setup-various.component.html',
  styleUrls: ['../setup.component.scss'],
  providers: [LibraryService]
})
export class SetupVariousComponent implements OnInit {
  private library: Library;
  private form: FormGroup;
  step = 2;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private libraryService: LibraryService,
    private setupDataService: SetupDataService
  ) {}

  ngOnInit(): void {
    this.setupDataService.setStep(2);
    this.library = this.setupDataService.getLibrary();

    this.form = this.formBuilder.group({
      'borrowDuration': ['', Validators.compose([Validators.required])],
      'subscriptionDuration': ['', Validators.required],
      'currency': ['', Validators.required],
      'itemStartNumber': [1, Validators.required]
    });
  }

  onSubmit(value: any): void {
    if (this.form.valid) {
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

      if (!this.form.controls['borrowDuration'].valid) {
        Materialize.toast('Borrow duration is empty', 4000);
      }
      if (!this.form.controls['subscriptionDuration'].valid) {
        Materialize.toast('Subscription duration is empty', 4000);
      }
      if (!this.form.controls['currency'].valid) {
        Materialize.toast('Currency is empty', 4000);
      }
      if (!this.form.controls['itemStartNumber'].valid) {
        Materialize.toast('Item start number is empty', 4000);
      }
    }
  }
}
