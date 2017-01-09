export class Title {
  mainTitle: string;
  subTitle: string;
  alternateTitles: string[];
  uniformTitle: string;

  constructor () {
    this.alternateTitles = [];
  }

  getMainTitle(): string {
    return this.mainTitle;
  }

  setMainTitle(value: string) {
    this.mainTitle = value;
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
