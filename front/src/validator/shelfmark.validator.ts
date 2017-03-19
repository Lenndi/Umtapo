import {FormControl} from '@angular/forms';

export class ShelfmarkValidator {
  static nbFields(control: FormControl) {
    return control.value <= 0 || control.value > 4 ? {'invalidShelfmark': true} : null;
  }
}
