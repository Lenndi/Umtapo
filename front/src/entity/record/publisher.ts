export class Publisher {
  publicationPlace: string;
  editorName: string;
  editorAddress: string;
  manufacturerName: string;
  manufacturerPlace: string;
  manufacturerAddress: string;

  getPublicationPlace(): string {
    return this.publicationPlace;
  }

  setPublicationPlace(value: string) {
    this.publicationPlace = value;
  }

  getEditorName(): string {
    return this.editorName;
  }

  setEditorName(value: string) {
    this.editorName = value;
  }

  getEditorAddress(): string {
    return this.editorAddress;
  }

  setEditorAddress(value: string) {
    this.editorAddress = value;
  }

  getManufacturerName(): string {
    return this.manufacturerName;
  }

  setManufacturerName(value: string) {
    this.manufacturerName = value;
  }

  getManufacturerPlace(): string {
    return this.manufacturerPlace;
  }

  setManufacturerPlace(value: string) {
    this.manufacturerPlace = value;
  }

  getManufacturerAddress(): string {
    return this.manufacturerAddress;
  }

  setManufacturerAddress(value: string) {
    this.manufacturerAddress = value;
  }
}
