import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {RecordService} from '../../../../../service/record.service';
import {ItemRegistrationDataService} from '../../../../../service/data-binding/item-registration-data.service';
import {HttpLoggerService} from '../../../../../service/http-logger.service';
import {Router} from '@angular/router';
import {IsbnValidator} from '../../../../../validator/isbn.validator';
import {logger} from '../../../../../environments/environment';
import {ToastrService} from 'ngx-toastr';

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
    public toastr: ToastrService,
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
  }

  onSearch(): void {
    if (this.form.valid) {
      let value = this.form.value;

      if (value.isbn) {
        this.searchByIsbn(value.isbn);
      } else if (value.title) {
        this.searchByTitle(value.title);
      } else {
        this.toastr.error('Merci d\'indiquer un titre ou un numéro ISBN', 'Oops');
      }
    } else {
      logger.info('Invalid form :', this.form);

      if (this.form.controls['isbn'].invalid) {
        this.toastr.error('Numéro ISBN invalide', 'Oops');
      }
    }
  }

  private searchByTitle(title: string) {
    this.dataService.isSearching = true;
    this.dataService.searchResults = [];
    this.recordService.findByTitle(title, 10, 0)
      .then(response => {
        this.dataService.searchResults = response.content;
        this.dataService.isSearching = false;

        if (response.totalPages > 1) {
          this.dataService.hasMoreRecords = true;
          this.getMoreRecords(title, 2);
        }
      })
      .catch(error => {
        if (error.status === 404) {
          this.dataService.searchMessage = `Aucun résultat avec le titre ${title}`;
        } else {
          this.httpLogger.error(error);
          this.toastr.error('Une erreur est survenue avec la bibliothèque distante', 'Oops');
        }
        this.dataService.isSearching = false;
      });
  }

  private searchByIsbn(isbn: string) {
    this.dataService.isSearching = true;

    this.recordService.findByIsbn(isbn)
      .then(response => {
        this.dataService.record = response;
        this.dataService.isSearching = false;

        this.router.navigate(['/cataloging/registration/save']);
      })
      .catch(error => {
        if (error.status === 404) {
          this.toastr.error('Nous n\'avons pas trouvé d\'ISBN correspondant', 'Oops');
        } else {
          this.httpLogger.error(error);
          this.toastr.error('Une erreur est survenue avec la bibliothèque distante', 'Oops');
        }
        this.dataService.isSearching = false;
      });
  }

  private getMoreRecords(title: string, page: number) {
    this.recordService.findByTitle(title, 10, page)
      .then(response => {
        this.dataService.searchResults = this.dataService.searchResults.concat(response.content);
        if (response.totalPages > page && this.dataService.hasMoreRecords) {
          this.getMoreRecords(title, page + 1);
        } else {
          this.dataService.hasMoreRecords = false;
        }
      })
      .catch( error => {
        this.httpLogger.error(error);
        this.toastr.error('Une erreur est survenue avec la bibliothèque distante', 'Oops');
        this.dataService.hasMoreRecords = false;
      });
  }
}
