import {Component, OnInit} from '@angular/core';
import {MdSnackBar} from '@angular/material';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {RecordService} from '../../../../service/record.service';
import {Record} from '../../../../entity/record/record';
import {ItemRegistrationDataService} from '../../../../service/data-binding/item-registration-data.service';

@Component({
  selector: 'app-item-search',
  templateUrl: './item-search.component.html',
  styleUrls: ['./item-search.component.scss'],
  providers: [RecordService]
})
export class ItemSearchComponent implements OnInit {
  private form: FormGroup;
  private isbn: FormControl;
  private title: FormControl;
  private records: Record[];
  private currentPage: number;
  private totalPage: number;

  constructor (
    private snackBar: MdSnackBar,
    private formBuilder: FormBuilder,
    private recordService: RecordService,
    private dataService: ItemRegistrationDataService
  ) {
    this.isbn = new FormControl('');
    this.title = new FormControl('');
    this.records = [];
    this.dataService.setSearchMessage('Pas encore de résultats…');
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      'isbn': this.isbn,
      'title': this.title
    });
  }

  onSearch(): void {
    let value = this.form.value;

    if (value.isbn) {
      this.recordService.findByIsbn(this.form.value.isbn)
        .then(response => {
          console.debug('title', response.getTitle().getTitle());
          this.dataService.setRecord(response);
        })
        .catch(error => {
          if (error.status === 404) {
            this.snackBar.open('Nous n\'avons pas trouvé d\'ISBN correspondant', 'OK');
          } else {
            this.snackBar.open('Une erreur est survenue avec la bibliothèque distante', 'OK');
          }
        });
    } else if (value.title) {
      this.recordService.findByTitle(this.form.value.title, 10, 1)
        .then(response => {
          console.debug('response', response);
          this.records.concat(response.getData());
          this.currentPage = response.getPage();
          this.totalPage = response.getTotalPage();
        })
        .catch(error => {
          console.debug('error', error);
          if (error.status === 404) {
            this.dataService.setSearchMessage(`Aucun résultat avec le titre ${value.title}`);
          } else {
            this.snackBar.open('Une erreur est survenue avec la bibliothèque distante', 'OK');
          }
        });
    } else {
      this.snackBar.open('Merci d\'indiquer un titre ou un numéro ISBN', 'OK');
    }
  }
}
