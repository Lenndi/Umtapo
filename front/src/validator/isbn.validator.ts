import {FormControl} from '@angular/forms';
import * as isbnUtils from 'isbn-utils';

export class IsbnValidator {
  static validNumber(control: FormControl) {
    return isbnUtils.parse(control.value) == null && control.value !== '' ? {'invalidISBN': true} : null;
  }
}
