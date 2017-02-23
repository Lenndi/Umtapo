import {Component, OnInit, OnDestroy} from '@angular/core';
import {Borrower} from '../../../../entity/borrower';
import {Router, ActivatedRoute} from '@angular/router';
import {BorrowerService} from '../../../../service/borrower.service';
import {MdSnackBar, MdSnackBarConfig} from '@angular/material';
import {Subscription} from '../../../../entity/subscription';
import {CirculationDataService} from '../../../../service/data-binding/circulation-data.service';

@Component({
  selector: 'umt-circulation-check-details',
  templateUrl: './circulation-check-details.component.html',
  styleUrls: ['./circulation-check-details.component.scss']
})
export class CirculationCheckDetailsComponent implements OnInit {
  private id: number;
  private borrower: Borrower;
  private config = new MdSnackBarConfig();
  private subscription: Subscription;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private borrowerService: BorrowerService,
              public dataService: CirculationDataService,
              private snackBar: MdSnackBar) {
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
    this.snackBar.open(`L'emprunteur n'a pas été trouvé`, 'OK', this.config);
  }

  backToCirculationComponent() {
    this.router.navigate(['circulation/selection']);
  }
}
