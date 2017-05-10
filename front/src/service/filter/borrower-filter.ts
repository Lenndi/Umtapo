export class BorrowerFilter {
  id: string;
  name: string;
  email: string;
  city: string;
  lateness: boolean;
  tooMuchLoans: boolean;

  public constructor() {
    this.id = '';
    this.email = '';
    this.name = '';
    this.city = '';
  }

  public getQueryString(): string {
    let request: string;

    request = `name=${this.name}&id=${this.id}&email=${this.email}&city=${this.city}`;
    if (this.lateness) {
      request += `&lateness=${this.lateness}`;
    }
    if (this.tooMuchLoans) {
      request += `&tooMuchLoans=${this.tooMuchLoans}`;
    }

    return request;
  }
}
