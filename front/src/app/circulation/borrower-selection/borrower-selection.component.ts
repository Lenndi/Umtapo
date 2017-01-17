import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {MdSnackBar, MdSnackBarConfig} from '@angular/material';
import {Borrower} from '../../../entity/borrower';
import {BorrowerService} from '../../../service/borrower.service';
import {jsonViewResolver} from '../../../config/jsonViewResolver';

@Component({
  selector: 'umt-borrower-selection',
  templateUrl: 'borrower-selection.component.html',
  styleUrls: ['borrower-selection.component.scss']
})
export class BorrowerSelectionComponent implements OnInit {
  form: FormGroup;
  borrowerName: string;
  borrowerId: FormControl;
  borrowers: Borrower[];
  borrower: Borrower;
  config;
  showDetails: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private borrowerService: BorrowerService,
    private router: Router,
    private snackBar: MdSnackBar

  ) {
    this.borrower = new Borrower();
    this.config = new MdSnackBarConfig();
    this.showDetails = false;
    this.borrowerId =  new FormControl('');
  }

  ngOnInit() {
    // TODO Dynamic search borrower
    this.borrowerService.findAll(jsonViewResolver.BorrowerSearchView)
      .then(borrowers => this.borrowers = borrowers);

    this.form = this.formBuilder.group({
      'borrowerId': this.borrowerId
    });
  }

  onSubmit(value: any): void {
    if (value.borrowerId !== '') {
    } else if (value.borrowerName !== '') {
      this.borrowerService.find(value.borrowerName)
        .then(response => this.borrower = response);
      this.showDetails = true;
      // TODO Asynchrone Problem
      // let subscirt: Subscription[] = this.borrower.getSubscriptions();
      // console.log(subscirt[0]);
    } else {
      this.config.duration = 1000;
      this.snackBar.open('Les champs du formulaire sont vides', 'OK', this.config);
    }

  }
}
