import {Injectable} from '@angular/core';
import {Title} from '../entity/record/title';
import {Creator} from '../entity/record/creator';
import {Publisher} from '../entity/record/publisher';
import {RecordDate} from '../entity/record/record-date';
import {Description} from '../entity/record/description';
import {Right} from '../entity/record/right';
import {Source} from '../entity/record/source';
import {Record} from '../entity/record/record';

@Injectable()
export class RecordDetailFormatterService {

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

  formatPublication(record: Record): string {
    let publisher: Publisher = record.publisher;
    let date: RecordDate = record.date;
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

  formatPhysicalDescription(record: Record): string {
    let description: Description = record.description;
    let formattedPhysicalDescription = '';

    if (description.mainPhysicalDescription) {
      formattedPhysicalDescription += description.mainPhysicalDescription;
    }
    if (description.secondaryPhysicalDescription) {
      formattedPhysicalDescription += ', ' + description.secondaryPhysicalDescription;
    }

    return formattedPhysicalDescription;
  }

  formatSubjects(record: Record): string {
    return record.subject.terms.reduce((formattedSubjects, term) => formattedSubjects += `, ${term}`);
  }

  formatSubtitles(record: Record): string {
    return record.language.subtitles.reduce((formattedSubtitles, subtitle) => formattedSubtitles += `, ${subtitle}`);
  }

  formatOrigin(record: Record): string {
    let right: Right = record.right;
    let source: Source = record.source;
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
