import {Component, OnInit} from '@angular/core';
import {FormGroup, FormBuilder, FormControl, Validators} from '@angular/forms';
import {PriceValidator} from '../../../../../validator/price.validator';

@Component({
  selector: 'app-internal-informations',
  templateUrl: './internal-informations.component.html',
  styleUrls: ['./internal-informations.component.scss']
})
export class InternalInformationsComponent implements OnInit {
  form: FormGroup;
  conditionEnum: Condition;
  internalId: FormControl;
  shelfmark1: FormControl;
  shelfmark2: FormControl;
  shelfmark3: FormControl;
  shelfmark4: FormControl;
  shelfmark5: FormControl;
  purchasePrice: FormControl;
  condition: FormControl;
  loanable: FormControl;
  library: FormControl;

  constructor(private formBuilder: FormBuilder) {
    this.internalId = new FormControl('');
    this.shelfmark1 = new FormControl('');
    this.shelfmark2 = new FormControl('');
    this.shelfmark3 = new FormControl('');
    this.shelfmark4 = new FormControl('');
    this.shelfmark5 = new FormControl('');
    this.purchasePrice = new FormControl('', [Validators.required, PriceValidator.isAPrice]);
    this.condition = new FormControl('');
    this.loanable = new FormControl(true);
    this.library = new FormControl('');
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      'internalId': this.internalId,
      'shelfmark1': this.shelfmark1,
      'shelfmark2': this.shelfmark2,
      'shelfmark3': this.shelfmark3,
      'shelfmark4': this.shelfmark4,
      'shelfmark5': this.shelfmark5,
      'purchasePrice': this.purchasePrice,
      'condition': this.condition,
      'loanable': this.loanable,
      'library': this.library
    });
  }

}
