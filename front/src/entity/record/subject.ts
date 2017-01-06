export class Subject {
  private terms: string[];
  private dewey: string;
  private universal: string;

  constructor () {
    this.terms = [];
  }

  getTerms(): string[] {
    return this.terms;
  }

  setTerms(value: string[]) {
    this.terms = value;
  }

  addTerm(term: string) {
    this.terms.push(term);
  }

  getDewey(): string {
    return this.dewey;
  }

  setDewey(value: string) {
    this.dewey = value;
  }

  getUniversal(): string {
    return this.universal;
  }

  setUniversal(value: string) {
    this.universal = value;
  }
}
