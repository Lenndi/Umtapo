import {Component, OnInit} from '@angular/core';
import {Borrower} from '../../../../entity/borrower';
import {Router} from '@angular/router';
import {CirculationDataService} from '../../../../service/data-binding/circulation-data.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'umt-circulation-check-details',
  templateUrl: './circulation-check-details.component.html',
  styleUrls: ['./circulation-check-details.component.scss']
})
export class CirculationCheckDetailsComponent implements OnInit {
  public borrower: Borrower;

  constructor(private router: Router,
              public dataService: CirculationDataService,
              public toastr: ToastrService) {
  }

  ngOnInit() {
   this.borrower = this.dataService.borrower;
  }

  backToCirculationComponent() {
    this.router.navigate(['circulation/selection']);
  }
}
