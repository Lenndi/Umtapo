export class Language {
  mainLanguage: string;
  originalLanguage: string;
  subtitles: string[];
  others: string[];

  constructor () {
    this.subtitles = [];
    this.others = [];
  }

  getMainLanguage(): string {
    return this.mainLanguage;
  }

  setMainLanguage(value: string) {
    this.mainLanguage = value;
  }

  getOriginalLanguage(): string {
    return this.originalLanguage;
  }

  setOriginalLanguage(value: string) {
    this.originalLanguage = value;
  }

  getSubtitles(): string[] {
    return this.subtitles;
  }

  setSubtitles(value: string[]) {
    this.subtitles = value;
  }

  addSubtitle(subtitle: string) {
    this.subtitles.push(subtitle);
  }

  getOthers(): string[] {
    return this.others;
  }

  setOthers(value: string[]) {
    this.others = value;
  }

  addOther(other: string) {
    this.others.push(other);
  }
}
