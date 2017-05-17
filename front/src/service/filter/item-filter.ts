export class ItemFilter {
  id: string;
  complexSearch: boolean;
  serialNumber: string;
  serialType: string;
  mainTitle: string;
  author: string;
  publisher: string;
  publicationDate: string;
  borrowed: boolean;

  public constructor() {
    this.id = '';
    this.complexSearch = false;
    this.serialNumber = '';
    this.serialType = '';
    this.mainTitle = '';
    this.author = '';
    this.publisher = '';
    this.publicationDate = '';
  }

  public getQueryString(): string {
    let request: string;

    request = `complexSearch=${this.complexSearch}&id=${this.id}&serialNumber=${this.serialNumber}`
      + `&serialType=${this.serialType}&mainTitle=${this.mainTitle}&author=${this.author}&publisher=${this.publisher}`
      + `&publicationDate=${this.publicationDate}`;
    if (this.borrowed) {
      request += `&borrowed=${this.borrowed}`;
    }

    return request;
  }
}
