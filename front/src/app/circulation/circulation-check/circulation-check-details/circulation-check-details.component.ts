import {Component, OnInit, OnDestroy} from '@angular/core';
import {Borrower} from '../../../../entity/borrower';
import {Router, ActivatedRoute} from '@angular/router';
import {BorrowerService} from '../../../../service/borrower.service';
import {MdSnackBar, MdSnackBarConfig} from '@angular/material';
import {Subscription} from '../../../../entity/subscription';

@Component({
  selector: 'app-circulation-check-details',
  templateUrl: './circulation-check-details.component.html',
  styleUrls: ['./circulation-check-details.component.scss']
})
export class CirculationCheckDetailsComponent implements OnInit, OnDestroy {
  private id: number;
  private sub: any;
  private borrower: Borrower;
  private config = new MdSnackBarConfig();
  private subscription: Subscription;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private borrowerService: BorrowerService,
              private snackBar: MdSnackBar) {
  }

  ngOnInit() {
    this.sub = this.activatedRoute.params.subscribe(params => {
      this.id = +params['id'];
    });
    this.borrowerService.find(this.id)
      .then(borrower => {
        this.borrower = borrower;
        this.subscription = this.borrower.subscriptions[0];
      })
      .catch(borrower => this.findBorrowerError());
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
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
