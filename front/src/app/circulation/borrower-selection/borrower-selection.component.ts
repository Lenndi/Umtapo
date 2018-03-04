import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {Borrower} from '../../../entity/borrower';
import {BorrowerService} from '../../../service/borrower.service';
import {CirculationDataService} from '../../../service/data-binding/circulation-data.service';
import {Observable} from 'rxjs';
import {TypeaheadMatch} from 'ngx-bootstrap';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'umt-borrower-selection',
  templateUrl: './borrower-selection.component.html',
  styleUrls: ['./borrower-selection.component.scss'],
})
export class BorrowerSelectionComponent implements OnInit, AfterViewInit {
  @ViewChild('borrowerIdInput') borrowerIdInput: ElementRef;
  form: FormGroup;
  borrowerId: FormControl;
  borrowerName: FormControl;
  borrowers: Observable<Borrower[]>;
  selectedBorrower: Borrower;
  showDetails: boolean;
  page: number = 0;
  size: number = 10;
  public dataSource: Observable<any>;

  constructor(private formBuilder: FormBuilder,
              private borrowerService: BorrowerService,
              private router: Router,
              public toastr: ToastrService,
              public dataService: CirculationDataService) {
    this.selectedBorrower = new Borrower();
    this.showDetails = false;
    this.borrowerId = new FormControl('');

    this.dataSource = Observable
      .create((observer: any) => {
        observer.next(this.form.value['borrowerName']);
      })
      .mergeMap((contains: string) => this.borrowerService.findByNameOrEmail(this.size, this.page, contains));
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      'borrowerId': this.borrowerId,
      'borrowerName': this.borrowerName
    });
  }

  ngAfterViewInit(): void {
    this.borrowerIdInput.nativeElement.focus();
  }

  typeaheadOnSelect(borrowerTypeahead: TypeaheadMatch): void {
    this.borrowerService.find(borrowerTypeahead.item.id)
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
        .catch(error => this.toastr.error(`Cet identifiant n'existe pas`, 'Oops'));

    } else {
      this.toastr.error('Les champs du formulaire sont vides', 'Oops');
    }
  }
}
