export class Library {
  private id: number;
  private name: string;
  private shelfMarkNb: number;
  private useDeweyClassification: boolean;
  private subscriptionDuration: number;
  private borrowDuration: number;
  private currency: string;

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
}
