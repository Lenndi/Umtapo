import {Component, OnDestroy, ViewChild} from '@angular/core';
import {ModalDirective} from 'ngx-bootstrap';
import {Subscription} from 'rxjs/Subscription';
import {Borrower} from '../../../../entity/borrower';
import {logger} from '../../../../environments/environment';
import {BorrowerService} from '../../../../service/borrower.service';
import {BorrowerDataService} from '../../../../service/data-binding/borrower-data.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'umt-borrower-delete',
  templateUrl: './borrower-delete.component.html',
  styleUrls: ['./borrower-delete.component.scss']
})
export class BorrowerDeleteComponent implements OnDestroy {

  @ViewChild('borrowerDeleteModal') public borrowerDeleteModal: ModalDirective;
  borrowerSubscription: Subscription;
  selectedBorrower: Borrower;

  constructor(
    private borrowerService: BorrowerService,
    public dataService: BorrowerDataService,
    public toastr: ToastrService
  ) {
    this.borrowerSubscription = this.dataService.selectedBorrower$.subscribe(
      borrower => this.selectedBorrower = borrower
    );
  }

  onDelete(): void {
    this.borrowerService
      .remove(this.selectedBorrower.id)
      .then(borrower => {
        this.hideModal();
        this.dataService.notifyUpdatedBorrower(borrower);
        this.toastr.info(`L'usager a été effacé`, 'OK');
      })
      .catch(response => {
        logger.error(response);
        this.hideModal();
        this.toastr.error(`Problème durant la suppression de l'usager`, 'Oops');
      });
  }

  public hideModal(): void {
    this.borrowerDeleteModal.hide();
  }

  public onHidden(): void {
    this.dataService.isDeleteShown = false;
  }

  ngOnDestroy() {
    this.borrowerSubscription.unsubscribe();
  }
}
