import {KeyValue} from './key-value';

export class CustomMap {
  array: KeyValue[];

  constructor() {
    this.array = [];
  }

  public add(key: string, value: any): CustomMap {
    this.array.push(new KeyValue(key, value));

    return this;
  }

  public remove(key: string): void {
    for (let i = 0; i < this.array.length; i++) {
      if (this.array[i].key === key) {
        this.array.splice(i, 1);
        break;
      }
    }
  }

  public get(key: string): KeyValue {
    let result: KeyValue = null;
    this.array.forEach(keyValue => {
      if (keyValue.key === key) {
        result = keyValue;
      }
    });

    return result;
  }
}
