import { Injectable } from '@angular/core';
import {logger} from '../environments/environment';

@Injectable()
export class HttpLoggerService {
  public error(error: any): Promise<any> {
    logger.error(error);

    return Promise.reject(error.message || error);
  }
}
