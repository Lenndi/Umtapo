export class Identifier {
  private recordIdentifier: string;
  private serialNumber: string;
  private serialType: string;
  private barCode: string;

  getRecordIdentifier(): string {
    return this.recordIdentifier;
  }

  setRecordIdentifier(value: string) {
    this.recordIdentifier = value;
  }

  getSerialNumber(): string {
    return this.serialNumber;
  }

  setSerialNumber(value: string) {
    this.serialNumber = value;
  }

  getSerialType(): string {
    return this.serialType;
  }

  setSerialType(value: string) {
    this.serialType = value;
  }

  getBarCode(): string {
    return this.barCode;
  }

  setBarCode(value: string) {
    this.barCode = value;
  }
}
