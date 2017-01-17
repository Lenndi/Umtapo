export class Language {
  mainLanguage: string;
  originalLanguage: string;
  subtitles: string[];
  others: string[];

  constructor () {
    this.subtitles = [];
    this.others = [];
  }
}
