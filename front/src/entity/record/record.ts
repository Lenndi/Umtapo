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
  }
}
