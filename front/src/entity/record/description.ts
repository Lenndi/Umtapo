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
}
