export class Library {
  id: number;
  name: string;
  shelfMarkNb: number;
  useDeweyClassification: boolean;
  subscriptionDuration: number;
  borrowDuration: number;
  currency: string;
  defaultZ3950: number;

  getId(): number {
    return this.id;
  }

  getName(): string {
    return this.name;
  }

  setName(value: string) {
    this.name = value;
  }

  getShelfMarkNb(): number {
    return this.shelfMarkNb;
  }

  setShelfMarkNb(value: number) {
    this.shelfMarkNb = value;
  }

  getUseDeweyClassification(): boolean {
    return this.useDeweyClassification;
  }

  setUseDeweyClassification(value: boolean) {
    this.useDeweyClassification = value;
  }

  getSubscriptionDuration(): number {
    return this.subscriptionDuration;
  }

  setSubscriptionDuration(value: number) {
    this.subscriptionDuration = value;
  }

  getBorrowDuration(): number {
    return this.borrowDuration;
  }

  setBorrowDuration(value: number) {
    this.borrowDuration = value;
  }

  getCurrency(): string {
    return this.currency;
  }

  setCurrency(value: string) {
    this.currency = value;
  }

  getDefaultZ3950(): number {
    return this.defaultZ3950;
  }

  setDefaultZ3950(value: number) {
    this.defaultZ3950 = value;
  }
}
