import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {MdSnackBar} from '@angular/material';
import {Borrower} from '../../../entity/borrower';
import {BorrowerService} from '../../../service/borrower.service';
import {jsonViewResolver} from '../../../config/jsonViewResolver';
import {CirculationDataService} from '../../../service/data-binding/circulation-data.service';
import {Observable, Subject} from 'rxjs'; // <-- import the module
import {Http, Response} from '@angular/http';
import {Loan} from "../../../entity/loan";
import {SelectModule} from 'ng2-select';



@Component({
  selector: 'umt-borrower-selection',
  templateUrl: './borrower-selection.component.html',
  styleUrls: ['./borrower-selection.component.scss'],
})
export class BorrowerSelectionComponent implements OnInit {
  form: FormGroup;
  borrowerId: FormControl;
  borrowerName: FormControl;
  borrowers: Observable<Borrower[]>;
  selectedBorrower: Borrower;
  showDetails: boolean;
  page: number = 0;
  size: number = 10;
  private searchBorrowers = new Subject<string>();

  constructor(private formBuilder: FormBuilder,
              private borrowerService: BorrowerService,
              private router: Router,
              private snackBar: MdSnackBar,
              public dataService: CirculationDataService,
              private http: Http) {
    this.selectedBorrower = new Borrower();
    this.showDetails = false;
    this.borrowerId = new FormControl('');
  }

  search(contains: string): void {
    this.searchBorrowers.next(contains);
  }

  ngOnInit() {
    this.borrowers = this.searchBorrowers
      .debounceTime(200)
      .distinctUntilChanged()
      .switchMap(contains => contains ?
        this.borrowerService.findPaginable(this.size, this.page, contains)
        : Observable.of<Borrower[]>([]))
      .catch(error => {
        return Observable.of<Borrower[]>([]);
      });

    this.form = this.formBuilder.group({
      'borrowerId': this.borrowerId,
      'borrowerName': this.borrowerName
    });
  }

  onSelect(id: number): void {
    this.borrowerService.find(id)
      .then(response => {
        this.selectedBorrower = response;
      });
    this.showDetails = true;
  }

  onSubmit(value: any): void {
    let id;
    if (value.borrowerId !== '' || this.selectedBorrower) {
      if (value.borrowerId !== '') {
        id = value.borrowerId;
      } else {
        if (this.selectedBorrower.id) {
          id = this.selectedBorrower.id;
        }
      }
      this.borrowerService.find(id)
        .then(response => {
          this.dataService.borrower = response;
          this.router.navigate(['circulation/check/']);
        })
        .catch(error => this.snackBar.open(`Cet identifiant n'existe pas`, 'OK'));

    } else {
      this.snackBar.open('Les champs du formulaire sont vides', 'OK');
    }
  }
}
