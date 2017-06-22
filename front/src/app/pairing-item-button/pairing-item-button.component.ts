import { Component, OnInit } from '@angular/core';
import {Pairing} from '../../util/pairing';
import {ToastrService} from 'ngx-toastr';
import {PairingService} from '../../service/pairing.service';
import {NewItemDataService} from '../../service/new-item-data.service';

@Component({
  selector: 'umt-pairing-item-button',
  templateUrl: './pairing-item-button.component.html',
  styleUrls: ['./pairing-item-button.component.scss']
})
export class PairingItemButtonComponent implements OnInit {

  pairing: Pairing = new Pairing;

  constructor(public toastr: ToastrService,
              private pairingService: PairingService,
              public dataService: NewItemDataService) {
  }

  ngOnInit(): void {
  }

  pairingItem() {
    this.dataService.isPairing = true;
    this.pairing.pairingType = 'BORROWER';
    this.pairing.itemId = this.dataService.item.id;
    this.pairingService.pairingItem(this.pairing)
      .then(res => {
        this.dataService.isPairing = false;
        this.toastr.success('Succès', 'Item lié à la carte');
      })
      .catch(err => {
        this.dataService.isPairing = false;
        if (err.status === 400) {
          this.toastr.error('Item non lié à la carte', 'Erreur');
        } else if (err.status === 408) {
          this.toastr.error('Item non lié à la carte, temps écoulé', 'Erreur');
        }
      });

  }
}
