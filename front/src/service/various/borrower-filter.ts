export class BorrowerFilter {
  id: string;
  name: string;
  email: string;
  city: string;

  public getQueryString(): string {
    return `name=${this.name}&id=${this.id}&email=${this.email}&city=${this.city}`;
  }
}
