export class Creator {
  name: string;
  secondName: string;
  date: string;
  titles: string;

  getName(): string {
    return this.name;
  }

  setName(value: string) {
    this.name = value;
  }

  getSecondName(): string {
    return this.secondName;
  }

  setSecondName(value: string) {
    this.secondName = value;
  }

  getDate(): string {
    return this.date;
  }

  setDate(value: string) {
    this.date = value;
  }

  getTitles(): string {
    return this.titles;
  }

  setTitles(value: string) {
    this.titles = value;
  }
}
