import { Component, OnInit } from '@angular/core';
import {Borrower} from '../../entity/borrower';
import {Subscription} from '../../entity/subscription';
import {BorrowerService} from '../../service/borrower.service';
import {ValidationService} from '../../service/ValidationService';

@Component({
  selector: 'app-new-borrower',
  templateUrl: './new-borrower.component.html',
  styleUrls: ['./new-borrower.component.scss']
})
export class NewBorrowerComponent implements OnInit {
  private form: FormGroup;
  private borrower: Borrower;
  private subscription: Subscription;

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private borrowerService: BorrowerService
  ) {}

  ngOnInit() {
    this.borrower = new Borrower();
    this.subscription = new Subscription();

    this.form = this.formBuilder.group({
      'name': ['', Validators.required],
      'email': ['', Validators.required, ValidationService.emailValidator],
      'birthdate': ['', Validators.required, ValidationService.dateValidator],
      'phone': ['', Validators.required],
      'address1': ['', Validators.required],
      'address2': ['', Validators.required],
      'zip': ['', Validators.required],
      'city': ['', Validators.required],
      'subcription-date': ['', Validators.required, ValidationService.dateValidator],
      'quota': ['', Validators.required],
      'contribution': ['', Validators.required],
      'comment': ['', Validators.required],
      'emailOptin': ['', Validators.required],
    });
  }
}
