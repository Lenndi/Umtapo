import {Component} from '@angular/core';
import {RecordDetailFormatterService} from '../../../../../../service/record-detail-formatter.service';
import {ItemRegistrationDataService} from '../../../../../../service/data-binding/item-registration-data.service';
import {Record} from '../../../../../../entity/record/record';
import {Title} from '../../../../../../entity/record/title';
import {Creator} from '../../../../../../entity/record/creator';
import {RecordDate} from '../../../../../../entity/record/record-date';
import {Publisher} from '../../../../../../entity/record/publisher';
import {Description} from '../../../../../../entity/record/description';
import {Subject} from '../../../../../../entity/record/subject';
import {Language} from '../../../../../../entity/record/language';
import {Identifier} from '../../../../../../entity/record/identifier';

@Component({
  selector: 'umt-manual-item-details',
  templateUrl: './manual-item-details.component.html',
  styleUrls: ['./manual-item-details.component.scss']
})
export class ManualItemDetailsComponent {

  isEditingISBN = true;
  isEditingTitle = true;
  isEditingAuthor = true;
  isEditingPublisher = true;
  isEditingDescription = true;
  isEditingPhysicalDescription = true;
  isEditingSubjects = true;
  isEditingLanguage = true;

  constructor(public dataService: ItemRegistrationDataService,
              public formatterService: RecordDetailFormatterService) {
    this.dataService.record = new Record();
  }

  editISBN(isbn: string) {
    if (!this.dataService.record.identifier) {
      this.dataService.record.identifier = new Identifier();
    }
    this.dataService.record.identifier.serialNumber = isbn;
    this.dataService.record.identifier.serialType = 'ISBN';
    this.isEditingISBN = false;
  }

  editTitle(title: string) {
    if (!this.dataService.record.title) {
      this.dataService.record.title = new Title();
    }
    this.dataService.record.title.mainTitle = title;
    this.isEditingTitle = false;
  }

  editAuthor(firstname: string, lastname: string) {
    if (!this.dataService.record.creator) {
      this.dataService.record.creator = new Creator();
    }
    this.dataService.record.creator.name = firstname;
    this.dataService.record.creator.secondName = lastname;
    this.isEditingAuthor = false;
  }

  editPublisher(publisher: string, date: string) {
    if (!this.dataService.record.date || !this.dataService.record.publisher) {
      this.dataService.record.date = new RecordDate();
      this.dataService.record.publisher = new Publisher();
    }

    this.dataService.record.date.publicationDate = date;
    this.dataService.record.publisher.editorName = publisher;
    this.isEditingPublisher = false;
  }

  editDescription(description: string) {
   if (!this.dataService.record.description) {
     this.dataService.record.description = new Description();
   }

   this.dataService.record.description.mainDescription = description;
   this.isEditingDescription = false;
  }

  editPhysicalDescription(description: string) {
    if (!this.dataService.record.description) {
      this.dataService.record.description = new Description();
    }

    this.dataService.record.description.mainPhysicalDescription = description;
    this.isEditingPhysicalDescription = false;
  }

  editSubjects(subjects: string) {
    if (!this.dataService.record.subject) {
      this.dataService.record.subject = new Subject();
    }

    this.dataService.record.subject.terms = subjects.split(',');
    this.isEditingSubjects = false;
  }

  editLanguage(language: string) {
    if (!this.dataService.record.language) {
      this.dataService.record.language = new Language();
    }

    this.dataService.record.language.mainLanguage = language;
    this.isEditingLanguage = false;
  }
}
