import {FormControl} from '@angular/forms';

export class PriceValidator {
  static isAPrice(control: FormControl) {
    let price: string = control.value;
    price = price.replace(',', '.');
    return isNaN(parseFloat(price)) ? {'invalidPrice': true} : null;
  }
}
