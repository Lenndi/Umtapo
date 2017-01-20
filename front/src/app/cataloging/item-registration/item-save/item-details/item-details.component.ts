import {Component} from '@angular/core';
import {ItemRegistrationDataService} from '../../../../../service/data-binding/item-registration-data.service';
import {Title} from '../../../../../entity/record/title';
import {Creator} from '../../../../../entity/record/creator';
import {Publisher} from '../../../../../entity/record/publisher';
import {RecordDate} from '../../../../../entity/record/record-date';
import {Description} from '../../../../../entity/record/description';
import {Subject} from '../../../../../entity/record/subject';
import {Language} from '../../../../../entity/record/language';
import {Right} from '../../../../../entity/record/right';
import {Source} from '../../../../../entity/record/source';

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.scss']
})
export class ItemDetailsComponent {

  constructor(public dataService: ItemRegistrationDataService) {}

  formatTitle(title: Title): string {
    let formattedTitle = '';

    if (title.mainTitle) {
      formattedTitle += title.mainTitle;
    }
    if (title.subTitle) {
      formattedTitle += ', ' + title.subTitle;
    }

    return formattedTitle;
  }

  formatAuthor(creator: Creator): string {
    let formattedCreator = '';

    if (creator.titles) {
      formattedCreator += creator.titles + ' ';
    }
    if (creator.secondName) {
      formattedCreator += creator.secondName + ' ';
    }
    if (creator.name) {
      formattedCreator += creator.name;
    }
    if (creator.date) {
      formattedCreator += ' (' + creator.date + ')';
    }

    return formattedCreator;
  }

  formatPublication(): string {
    let publisher: Publisher = this.dataService.record.publisher;
    let date: RecordDate = this.dataService.record.date;
    let formattedPublication = '';

    if (publisher.publicationPlace) {
      formattedPublication += publisher.publicationPlace + ' : ';
    }
    if (publisher.editorName) {
      formattedPublication += publisher.editorName;
    }
    if (date.publicationDate) {
      formattedPublication += ', ' + date.publicationDate;
    }

    return formattedPublication;
  }

  formatPhysicalDescription(): string {
    let description: Description = this.dataService.record.description;
    let formattedPhysicalDescription = '';

    if (description.mainPhysicalDescription) {
      formattedPhysicalDescription += description.mainPhysicalDescription;
    }
    if (description.secondaryPhysicalDescription) {
      formattedPhysicalDescription += ', ' + description.secondaryPhysicalDescription;
    }

    return formattedPhysicalDescription;
  }

  formatSubjects(): string {
    let subject: Subject = this.dataService.record.subject;
    let formattedSubjects = '';
    let termNb = 1;

    for (let term in subject.terms) {
      if (termNb > 1) {
        formattedSubjects += ', ';
      }
      formattedSubjects += term;
      termNb++;
    }

    return formattedSubjects;
  }

  formatSubtitles(): string {
    let language: Language = this.dataService.record.language;
    let formattedSubtitles = '';
    let subNb = 1;

    for (let subtitle in language.subtitles) {
      if (subNb > 1) {
        formattedSubtitles += ', ';
      }
      formattedSubtitles += subtitle;
      subNb++;
    }

    return formattedSubtitles;
  }

  formatOrigin(): string {
    let right: Right = this.dataService.record.right;
    let source: Source = this.dataService.record.source;
    let origin = '';

    if (source.library) {
      origin += source.library;
    }
    if (right.recordOrigin) {
      origin += ' | ' + right.recordOrigin;
    }
    if (right.transactionDate) {
      origin += ' | ' + right.transactionDate;
    }

    return origin;
  }
}
