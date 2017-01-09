export class Identifier {
  recordIdentifier: string;
  serialNumber: string;
  serialType: string;
  barCode: string;

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
