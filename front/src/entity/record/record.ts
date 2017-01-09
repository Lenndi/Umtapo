import {Title} from './title';
import {Creator} from './creator';
import {Subject} from './subject';
import {Description} from './description';
import {Publisher} from './publisher';
import {RecordDate} from './record-date';
import {Type} from './type';
import {Identifier} from './identifier';
import {Source} from './source';
import {Language} from './language';
import {Coverage} from './coverage';
import {Right} from './right';

export class Record {
  title: Title;
  creator: Creator;
  subject: Subject;
  description: Description;
  publisher: Publisher;
  contributors: Creator[];
  date: RecordDate;
  type: Type;
  identifier: Identifier;
  source: Source;
  language: Language;
  coverage: Coverage;
  right: Right;

  constructor () {
    this.contributors = [];
  }

  getTitle(): Title {
    return this.title;
  }

  setTitle(value: Title) {
    this.title = value;
  }

  getCreator(): Creator {
    return this.creator;
  }

  setCreator(value: Creator) {
    this.creator = value;
  }

  getSubject(): Subject {
    return this.subject;
  }

  setSubject(value: Subject) {
    this.subject = value;
  }

  getDescription(): Description {
    return this.description;
  }

  setDescription(value: Description) {
    this.description = value;
  }

  getPublisher(): Publisher {
    return this.publisher;
  }

  setPublisher(value: Publisher) {
    this.publisher = value;
  }

  getContributors(): Creator[] {
    return this.contributors;
  }

  setContributors(value: Creator[]) {
    this.contributors = value;
  }

  addContributor(contributor: Creator) {
    this.contributors.push(contributor);
  }

  getDate(): RecordDate {
    return this.date;
  }

  setDate(value: RecordDate) {
    this.date = value;
  }

  getType(): Type {
    return this.type;
  }

  setType(value: Type) {
    this.type = value;
  }

  getIdentifier(): Identifier {
    return this.identifier;
  }

  setIdentifier(value: Identifier) {
    this.identifier = value;
  }

  getSource(): Source {
    return this.source;
  }

  setSource(value: Source) {
    this.source = value;
  }

  getLanguage(): Language {
    return this.language;
  }

  setLanguage(value: Language) {
    this.language = value;
  }

  getCoverage(): Coverage {
    return this.coverage;
  }

  setCoverage(value: Coverage) {
    this.coverage = value;
  }

  getRight(): Right {
    return this.right;
  }

  setRight(value: Right) {
    this.right = value;
  }
}
