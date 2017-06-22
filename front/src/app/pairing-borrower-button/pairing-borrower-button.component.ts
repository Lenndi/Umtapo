import {Component, OnInit} from '@angular/core';
import {Pairing} from '../../util/pairing';
import {ToastrService} from 'ngx-toastr';
import {PairingService} from '../../service/pairing.service';
import {NewBorrowerDataService} from '../../service/data-binding/new-borrower-data.service';

@Component({
  selector: 'umt-pairing-borrower-button',
  templateUrl: './pairing-borrower-button.component.html',
  styleUrls: ['./pairing-borrower-button.component.scss']
})
export class PairingBorrowerButtonComponent implements OnInit {
  pairing: Pairing = new Pairing;

  constructor(public toastr: ToastrService,
              private pairingService: PairingService,
              public dataService: NewBorrowerDataService) {
  }

  ngOnInit(): void {
  }

  pairingBorrower() {
    this.dataService.isPairing = true;
    this.pairing.pairingType = 'BORROWER';
    this.pairing.borrowerId = this.dataService.borrower.id;
    this.pairingService.pairingCard(this.pairing)
      .then(res => {
        this.dataService.isPairing = false;
        this.toastr.success('Succès', 'Emprunteur lié à la carte');
      })
      .catch(err => {
        this.dataService.isPairing = false;
        if (err.status === 400) {
          this.toastr.error('Emprunteur non lié à la carte', 'Erreur');
        } else if (err.status === 408) {
          this.toastr.error('Emprunteur non lié à la carte, temps écoulé', 'Erreur');
        }
      });

  }
}
