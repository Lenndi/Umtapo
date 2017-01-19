import {FormControl} from '@angular/forms';

export class VariousValidator {
  static positive(control: FormControl) {
    return control.value <= 0 ? {'must be a positive number': true} : null;
  }
}
