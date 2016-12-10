export class Z3950 {
  private id: number;
  private name: string;
  private url: string;
  private port: number;
  private syntax: string;
  private database: Map<string, string>;
  private options: Map<string, string>;

  getId(): number {
    return this.id;
  }

  setId(value: number) {
    this.id = value;
  }

  getName(): string {
    return this.name;
  }

  setName(value: string) {
    this.name = value;
  }

  getUrl(): string {
    return this.url;
  }

  setUrl(value: string) {
    this.url = value;
  }

  getPort(): number {
    return this.port;
  }

  setPort(value: number) {
    this.port = value;
  }

  getSyntax(): string {
    return this.syntax;
  }

  setSyntax(value: string) {
    this.syntax = value;
  }

  getDatabase(): Map<string, string> {
    return this.database;
  }

  setDatabase(value: Map<string, string>) {
    this.database = value;
  }

  getOptions(): Map<string, string> {
    return this.options;
  }

  setOptions(value: Map<string, string>) {
    this.options = value;
  }
}
