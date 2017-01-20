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
        // TODO: real error handling
        console.log(error);
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
        console.debug('borrower', this.selectedBorrower);
      });
    this.showDetails = true;

  }

  onSubmit(value: any): void {
    if (value.borrowerId !== '' || this.selectedBorrower) {
      if (value.borrowerId !== '') {
        this.router.navigate(['circulation/check/' + value.borrowerId]);
      }
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
