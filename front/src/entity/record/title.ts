export class Title {
  private title: string;
  private subTitle: string;
  private alternateTitles: string[];
  private uniformTitle: string;

  constructor () {
    this.alternateTitles = [];
  }

  getTitle(): string {
    return this.title;
  }

  setTitle(value: string) {
    this.title = value;
  }

  getSubTitle(): string {
    return this.subTitle;
  }

  setSubTitle(value: string) {
    this.subTitle = value;
  }

  getAlternateTitles(): string[] {
    return this.alternateTitles;
  }

  setAlternateTitles(value: string[]) {
    this.alternateTitles = value;
  }

  addAlternateTitle(title: string) {
    this.alternateTitles.push(title);
  }

  getUniformTitle(): string {
    return this.uniformTitle;
  }

  setUniformTitle(value: string) {
    this.uniformTitle = value;
  }
}
