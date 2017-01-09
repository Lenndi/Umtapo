export class Source {
  library: string;
  url: string;

  getLibrary(): string {
    return this.library;
  }

  setLibrary(value: string) {
    this.library = value;
  }

  getUrl(): string {
    return this.url;
  }

  setUrl(value: string) {
    this.url = value;
  }
}
