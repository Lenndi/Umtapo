import { Injectable } from '@angular/core';
import {Library} from '../../entity/library';

@Injectable()
export class SetupDataService {
  private library: Library;
  private step: number;
  private precedence: boolean;

  getLibrary(): Library {
    return this.library;
  }

  setLibrary(value: Library) {
    this.library = value;
  }

  getStep(): number {
    return this.step;
  }

  setStep(value: number) {
    this.step = value;
    this.precedence = value > 1;
  }

  getPrecedence(): boolean {
    return this.precedence;
  }

  setPrecedence(value: boolean) {
    this.precedence = value;
  }
}
