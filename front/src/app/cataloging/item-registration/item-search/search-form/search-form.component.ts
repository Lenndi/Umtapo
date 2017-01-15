import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, FormBuilder} from '@angular/forms';
import {MdSnackBar} from '@angular/material';
import {RecordService} from '../../../../../service/record.service';
import {ItemRegistrationDataService} from '../../../../../service/data-binding/item-registration-data.service';
import {HttpLoggerService} from '../../../../../service/http-logger.service';
import {Router} from '@angular/router';
import {IsbnValidator} from '../../../../../validator/isbn.validator';
import {logger} from '../../../../../environments/environment';
import * as isbnUtils from 'isbn-utils';

@Component({
  selector: 'umt-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.scss']
})
export class SearchFormComponent implements OnInit {
  form: FormGroup;
  isbn: FormControl;
  title: FormControl;

  constructor (
    private snackBar: MdSnackBar,
    private formBuilder: FormBuilder,
    private recordService: RecordService,
    public dataService: ItemRegistrationDataService,
    private httpLogger: HttpLoggerService,
    private router: Router
  ) {
    this.isbn = new FormControl('', IsbnValidator.validNumber);
    this.title = new FormControl('');
    this.dataService.searchMessage = 'Pas encore de résultats…';
    this.dataService.isSearching = false;
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'isbn': this.isbn,
      'title': this.title
    });
    // this.cardContentHeight = document.getElementById('side-nav').clientHeight - 160;
  }

  onSearch(): void {
    if (this.form.valid) {
      let value = this.form.value;

      if (value.isbn) {
        this.searchByIsbn(value.isbn);
      } else if (value.title) {
        this.searchByTitle(value.title);
      } else {
        this.snackBar.open('Merci d\'indiquer un titre ou un numéro ISBN', 'OK');
      }
    } else {
      logger.info('Invalid form :', this.form);

      if (this.form.controls['isbn'].invalid) {
        this.snackBar.open('Numéro ISBN invalide', 'OK');
      }
    }
  }

  private searchByTitle(title: string) {
    this.dataService.isSearching = true;
    this.dataService.searchResults = [];
    this.recordService.findByTitle(title, 10, 1)
      .then(response => {
        this.dataService.searchResults = response.data;
        this.dataService.isSearching = false;

        if (response.totalPage > 1) {
          this.dataService.hasMoreRecords = true;
          this.getMoreRecords(title, 2);
        }
      })
      .catch(error => {
        if (error.status === 404) {
          this.dataService.searchMessage = `Aucun résultat avec le titre ${title}`;
        } else {
          this.httpLogger.error(error);
          this.snackBar.open('Une erreur est survenue avec la bibliothèque distante', 'OK');
        }
        this.dataService.isSearching = false;
      });
  }

  private searchByIsbn(isbn: string) {
    this.dataService.isSearching = true;
    let parsedIsbn = isbnUtils.parse(isbn);
    let normalizedIsbn = parsedIsbn.isIsbn13() ? parsedIsbn.asIsbn13() : parsedIsbn.asIsbn10();

    this.recordService.findByIsbn(normalizedIsbn)
      .then(response => {
        this.dataService.record = response;
        this.dataService.isSearching = false;

        this.router.navigate(['/cataloging/registration/save']);
      })
      .catch(error => {
        if (error.status === 404) {
          this.snackBar.open('Nous n\'avons pas trouvé d\'ISBN correspondant', 'OK');
        } else {
          this.httpLogger.error(error);
          this.snackBar.open('Une erreur est survenue avec la bibliothèque distante', 'OK');
        }
        this.dataService.isSearching = false;
      });
  }

  private getMoreRecords(title: string, page: number) {
    this.recordService.findByTitle(title, 10, page)
      .then(response => {
        this.dataService.searchResults = this.dataService.searchResults.concat(response.data);
        if (response.totalPage > page) {
          this.getMoreRecords(title, page + 1);
        } else {
          this.dataService.hasMoreRecords = false;
        }
      })
      .catch( error => {
        this.httpLogger.error(error);
        this.snackBar.open('Une erreur est survenue avec la bibliothèque distante', 'OK');
        this.dataService.hasMoreRecords = false;
      });
  }
}
