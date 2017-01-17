import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {MdSnackBar} from '@angular/material';
import {Borrower} from '../../../entity/borrower';
import {BorrowerService} from '../../../service/borrower.service';
import {jsonViewResolver} from '../../../config/jsonViewResolver';
import {CirculationDataService} from '../../../service/data-binding/circulation-data.service';

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
  selectedBorrower: Borrower;
  showDetails: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private borrowerService: BorrowerService,
    private router: Router,
    private snackBar: MdSnackBar,
    public dataService: CirculationDataService
  ) {
    this.selectedBorrower = new Borrower();
    this.showDetails = false;
    this.borrowerId =  new FormControl('');
  }

  ngOnInit() {
    // TODO Dynamic search borrower (limited to 5 results ?)
    this.borrowerService.findAll(jsonViewResolver.BorrowerSearchView)
      .then(borrowers => this.borrowers = borrowers);

    this.form = this.formBuilder.group({
      'borrowerId': this.borrowerId
    });
  }

  onSelect(id: number): void {
    this.borrowerService.find(id)
      .then(response => this.selectedBorrower = response);
    this.showDetails = true;
  }

  onSubmit(value: any): void {
    if (value.borrowerId !== '' || this.selectedBorrower) {
      if (!this.selectedBorrower) {
        this.borrowerService.find(value.borrowerId)
          .then(response => this.selectedBorrower = response);
      }
      this.dataService.borrower = this.selectedBorrower;
      // TODO: Redirect to next component
    } else {
      this.snackBar.open('Les champs du formulaire sont vides', 'OK');
    }
  }
}
