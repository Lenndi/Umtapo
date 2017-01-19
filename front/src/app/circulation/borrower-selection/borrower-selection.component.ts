import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {MdSnackBar} from '@angular/material';
import {Borrower} from '../../../entity/borrower';
import {BorrowerService} from '../../../service/borrower.service';
import {jsonViewResolver} from '../../../config/jsonViewResolver';
import {CirculationDataService} from '../../../service/data-binding/circulation-data.service';
import {Observable, Subject} from "rxjs"; // <-- import the module
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
  borrowers: Observable<Array<Borrower>>;
  selectedBorrower: Borrower;
  showDetails: boolean;
  page: number = 0;

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

  ngOnInit() {
    this.form = this.formBuilder.group({
      'borrowerId': this.borrowerId,
      'borrowerName': this.borrowerName
    });
  }

  searchBorrowers(event) {
    console.log(event);
    this.getPage(this.page, 10, this.form.value.borrowerName);
  }

  onScroll(event) {
    console.log('scroll event', event);
  }

  getPage(page: number, size: number, contains: string) {
    let api;
    api = `http://localhost:8080/borrowers/${size}/${page}/${contains}`;
    this.borrowers = this.http.get(api)
      .map((res: Response) => res.json().content)
      .debounceTime(300)        // wait for 300ms pause in events
      .distinctUntilChanged()
      .catch(error => {
        // TODO: real error handling
        console.log(error);
        return Observable.of<Borrower[]>([]);
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
