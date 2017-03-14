import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {Borrower} from '../../../../entity/borrower';
import {Router, ActivatedRoute} from '@angular/router';
import {BorrowerService} from '../../../../service/borrower.service';
import {Subscription} from '../../../../entity/subscription';
import {CirculationDataService} from '../../../../service/data-binding/circulation-data.service';
import {ToastsManager} from 'ng2-toastr';

@Component({
  selector: 'umt-circulation-check-details',
  templateUrl: './circulation-check-details.component.html',
  styleUrls: ['./circulation-check-details.component.scss']
})
export class CirculationCheckDetailsComponent implements OnInit {
  private id: number;
  private borrower: Borrower;
  private subscription: Subscription;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private borrowerService: BorrowerService,
              public dataService: CirculationDataService,
              public toastr: ToastsManager,
              public vRef: ViewContainerRef) {
  }

  ngOnInit() {
   this.borrower = this.dataService.borrower;
  }

  // #########################################################################################################
  // ########################################### INTERNAL FUNCTION ###########################################
  // #########################################################################################################

  /**
   * If Find Borrower Promise is not successful .
   */
  findBorrowerError() {
    this.toastr.error(`L'emprunteur n'a pas été trouvé`, 'Oops', {toastLife: 2000});
  }

  backToCirculationComponent() {
    this.router.navigate(['circulation/selection']);
  }
}
