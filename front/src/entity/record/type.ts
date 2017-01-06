export class Type {
  private types: string[];

  getTypes(): string[] {
    return this.types;
  }

  setTypes(value: string[]) {
    this.types = value;
  }

  addType(type: string) {
    this.types.push(type);
  }
}
