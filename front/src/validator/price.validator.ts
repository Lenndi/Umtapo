import {FormControl} from '@angular/forms';

export class PriceValidator {
  static isAPrice(control: FormControl) {
    let price = '';
    if (control.value != null) {
      price = control.value.toString();
      price = price.replace(',', '.');
    }
    return isNaN(parseFloat(price)) ? {'invalidPrice': true} : null;
  }
}
