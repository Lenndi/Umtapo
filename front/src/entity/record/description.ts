export class Description {
  mainDescription: string;
  otherDescriptions: string[];
  mainPhysicalDescription: string;
  secondaryPhysicalDescription: string;
  format: string;
  associatedMaterial: string;

  constructor () {
    this.otherDescriptions = [];
  }

  getMainDescription(): string {
    return this.mainDescription;
  }

  setMainDescription(value: string) {
    this.mainDescription = value;
  }

  getOtherDescriptions(): string[] {
    return this.otherDescriptions;
  }

  setOtherDescriptions(value: string[]) {
    this.otherDescriptions = value;
  }

  addOtherDescription(otherDescription: string) {
    this.otherDescriptions.push(otherDescription);
  }

  getMainPhysicalDescription(): string {
    return this.mainPhysicalDescription;
  }

  setMainPhysicalDescription(value: string) {
    this.mainPhysicalDescription = value;
  }

  getSecondaryPhysicalDescription(): string {
    return this.secondaryPhysicalDescription;
  }

  setSecondaryPhysicalDescription(value: string) {
    this.secondaryPhysicalDescription = value;
  }

  getFormat(): string {
    return this.format;
  }

  setFormat(value: string) {
    this.format = value;
  }

  getAssociatedMaterial(): string {
    return this.associatedMaterial;
  }

  setAssociatedMaterial(value: string) {
    this.associatedMaterial = value;
  }
}
