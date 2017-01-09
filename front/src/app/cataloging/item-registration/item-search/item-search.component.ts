import {Component, OnInit} from '@angular/core';
import {MdSnackBar} from '@angular/material';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {RecordService} from '../../../../service/record.service';
import {ItemRegistrationDataService} from '../../../../service/data-binding/item-registration-data.service';
import {HttpLoggerService} from '../../../../service/http-logger.service';
import * as isbnUtils from 'isbn-utils';
import {IsbnValidator} from '../../../../validator/isbn.validator';
import {logger} from '../../../../environments/environment';

@Component({
  selector: 'app-item-search',
  templateUrl: './item-search.component.html',
  styleUrls: ['./item-search.component.scss'],
  providers: [RecordService]
})
export class ItemSearchComponent implements OnInit {
  form: FormGroup;
  isbn: FormControl;
  title: FormControl;
  currentPage: number;
  totalPage: number;

  constructor (
    private snackBar: MdSnackBar,
    private formBuilder: FormBuilder,
    private recordService: RecordService,
    public dataService: ItemRegistrationDataService,
    private httpLogger: HttpLoggerService
  ) {
    this.isbn = new FormControl('', IsbnValidator.validNumber);
    this.title = new FormControl('');
    this.dataService.setSearchMessage('Pas encore de résultats…');
    this.dataService.setIsSearching(false);
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'isbn': this.isbn,
      'title': this.title
    });
  }

  onSearch(): void {
    if (this.form.valid) {
      let value = this.form.value;

      if (value.isbn) {
        this.dataService.setIsSearching(true);
        let parsedIsbn = isbnUtils.parse(value.isbn);
        let normalizedIsbn = parsedIsbn.isIsbn13() ? parsedIsbn.asIsbn13() : parsedIsbn.asIsbn10();

        this.recordService.findByIsbn(normalizedIsbn)
          .then(response => {
            this.dataService.setRecord(response);
            this.dataService.setIsSearching(false);
          })
          .catch(error => {
            if (error.status === 404) {
              this.snackBar.open('Nous n\'avons pas trouvé d\'ISBN correspondant', 'OK');
            } else {
              this.httpLogger.error(error);
              this.snackBar.open('Une erreur est survenue avec la bibliothèque distante', 'OK');
            }
          });
      } else if (value.title) {
        this.dataService.setIsSearching(true);
        this.recordService.findByTitle(value.title, 10, 1)
          .then(response => {
            this.dataService.setSearchResults(response.data);
            this.currentPage = response.page;
            this.totalPage = response.totalPage;
            this.dataService.setIsSearching(false);
          })
          .catch(error => {
            if (error.status === 404) {
              this.dataService.setSearchMessage(`Aucun résultat avec le titre ${value.title}`);
            } else {
              this.httpLogger.error(error);
              this.snackBar.open('Une erreur est survenue avec la bibliothèque distante', 'OK');
            }
          });
      } else {
        this.snackBar.open('Merci d\'indiquer un titre ou un numéro ISBN', 'OK');
      }
    } else {
      logger.info('Invalid form :', this.form.value);

      if (this.form.controls['isbn'].invalid) {
        this.snackBar.open('Numéro ISBN invalide', 'OK');
      }
    }
  }
}
