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
    this.config.duration = 1000;
    if (value.idBorrower !== '') {
      let idExist = this.checkIfIdExist(value.idBorrower);
      if(!idExist){
        this.snackBar.open(`L'identifiant entré n'existe pas`, 'OK', this.config);
      } else {
        this.router.navigate(['circulation/check', value.idBorrower]);
      }
    } else if (value.borrowerNames !== '') {
      this.borrowerService.find(value.borrowerNames)
        .then(borrower => {
          this.borrower = borrower;
          this.subscription = this.borrower.subscriptions[0];
        });
      this.showDetails = true;
    } else {
      this.snackBar.open('Les champs du formulaire sont vides', 'OK', this.config);
    }
  }

  // #########################################################################################################
  // ########################################### INTERNAL FUNCTION ###########################################
  // #########################################################################################################

  private checkIfIdExist(id: number): boolean{
    for (let borrower of this.borrowers) {
      if(borrower.id == id){
        return true;
      }
    }
    return false;
  }
}
