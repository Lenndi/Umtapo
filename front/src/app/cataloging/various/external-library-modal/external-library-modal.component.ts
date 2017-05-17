import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Library} from '../../../../entity/library';
import {LibraryService} from '../../../../service/library.service';
import {logger} from '../../../../environments/environment';
import {ModalDirective} from 'ngx-bootstrap';
import {ToastrService} from 'ngx-toastr';
import {VariousItemDataService} from '../../../../service/data-binding/various-item-data.service';

@Component({
  selector: 'umt-external-library-modal',
  templateUrl: './external-library-modal.component.html',
  styleUrls: ['./external-library-modal.component.scss']
})
export class ExternalLibraryModalComponent implements OnInit {

  @ViewChild('externalLibraryModal')
  public externalLibraryModal: ModalDirective;

  externalLibraryForm: FormGroup;
  externalLibraryName: FormControl;

  constructor(
      private libraryService: LibraryService,
      public toastr: ToastrService,
      private formBuilder: FormBuilder,
      private variousItemDataService: VariousItemDataService
  ) {
    this.externalLibraryName = new FormControl('');
  }

  ngOnInit(): void {
    this.externalLibraryForm = this.formBuilder.group({
      'externalLibraryName': this.externalLibraryName
    });
  }

  onLibrarySubmit(): void {
    if (this.externalLibraryForm.valid) {
      let value = this.externalLibraryForm.value;
      let library: Library = new Library();

      library.name = value.externalLibraryName;

      this.libraryService.saveExternal(library)
        .then(library => {
          this.toastr.info(`Bibliothèque ${library.name} ajoutée`, 'Nouvelle bibliothèque');
          this.populateExternalLibraries();
          this.externalLibraryModal.hide();
        })
        .catch(error => logger.error(error));
    } else {
      this.toastr.error('Impossible de créer la bibliothèque tiers', 'Oops');
      logger.info('Invalid form :', this.externalLibraryForm);
    }
  }

  public populateExternalLibraries(): void {
    this.libraryService.findExternalLibraries()
      .then(externalLibraries => this.variousItemDataService.externalLibraries = externalLibraries)
      .catch(error => logger.error(error));
  }

  public showModal() {
    this.externalLibraryModal.show();
  }

  public hideModal() {
    this.externalLibraryModal.hide();
  }
}
