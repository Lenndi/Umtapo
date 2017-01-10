import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {BorrowerService} from '../../../service/borrower.service';
import {Borrower} from '../../../entity/borrower';
import {logger} from '../../../environments/environment';
import {jsonViewResolver} from '../../../config/jsonViewResolver';
import {MdSnackBar, MdSnackBarConfig} from '@angular/material';
import {plainToClass} from 'class-transformer';
import {Subscription} from "../../../entity/subscription";


@Component({
  selector: 'app-circulation-borrower-selection',
  templateUrl: './circulation-borrower-selection.component.html',
  styleUrls: ['./circulation-borrower-selection.component.scss']
})
export class CirculationBorrowerSelectionComponent implements OnInit {
  private form: FormGroup;
  private borrowerNames: FormControl;
  private idBorrower: FormControl;
  private borrowers: Borrower[];
  private borrower: Borrower = new Borrower();
  private config = new MdSnackBarConfig();
  private showDetails: boolean = false;
  private subscription: Subscription;


  constructor(
    private formBuilder: FormBuilder,
    private borrowerService: BorrowerService,
    private router: Router,
    private snackBar: MdSnackBar

  ) {
    this.idBorrower =  new FormControl('');
    this.borrowerNames = new FormControl('');
  }

  ngOnInit() {
    // TODO Dynamic search borrower
    this.borrowerService.findAll(jsonViewResolver.BorrowerSearchView)
      .then(borrowers => this.borrowers = borrowers);

    this.form = this.formBuilder.group({
      'idBorrower': this.idBorrower,
      'borrowerNames': this.borrowerNames
    });
  }

  onSubmit(value: any): void {
    if (value.idBorrower !== '') {
    } else if (value.borrowerNames !== '') {
      this.borrowerService.find(value.borrowerNames)
        .then(borrower => {
          this.borrower = borrower;
        });
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
