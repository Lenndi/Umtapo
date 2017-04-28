import {Component, ViewChild} from '@angular/core';
import {ModalDirective} from 'ngx-bootstrap';
import {Subscription} from 'rxjs/Subscription';
import {Borrower} from '../../../../entity/borrower';
import {logger} from '../../../../environments/environment';
import {BorrowerService} from '../../../../service/borrower.service';
import {BorrowerDataService} from '../../../../service/data-binding/borrower-data.service';
import {Action} from '../../../../enumeration/Action';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'umt-borrower-delete',
  templateUrl: './borrower-delete.component.html',
  styleUrls: ['./borrower-delete.component.scss']
})
export class BorrowerDeleteComponent {

  @ViewChild('borrowerDeleteModal') public borrowerDeleteModal: ModalDirective;
  borrowerSubscription: Subscription;
  selectedBorrower: Borrower;

  constructor(
    private borrowerService: BorrowerService,
    private dataService: BorrowerDataService,
    public toastr: ToastrService
  ) {
    this.borrowerSubscription = this.dataService.selectedBorrower$.subscribe(
      borrower => {
        if (this.dataService.action === Action.DELETE) {
          this.selectedBorrower = borrower;
          this.borrowerDeleteModal.show();
        }
      }
    );
  }

  onDelete(): void {
    this.borrowerService
      .remove(this.selectedBorrower.id)
      .then(borrower => {
        this.borrowerDeleteModal.hide();
        this.dataService.notifyUpdatedBorrower(borrower);
        this.toastr.info(`L'usager a été effacé`, 'OK');
      })
      .catch(response => {
        logger.error(response);
        this.borrowerDeleteModal.hide();
        this.toastr.error(`Problème durant la suppression de l'usager`, 'Oops');
      });
  }

  ngOnDestroy() {
    this.borrowerSubscription.unsubscribe();
  }
}
