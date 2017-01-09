import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import {HttpLoggerService} from './http-logger.service';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import {Record} from '../entity/record/record';
import {GenericRestWrapper} from '../entity/generic-rest-wrapper';

@Injectable()
export class RecordService {
  private recordUrl: string;
  private headers: Headers;

  constructor(private http: Http, private httpLogger: HttpLoggerService) {
    this.recordUrl = environment.api_url + api.record;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  findByTitle(title: string, resultSize: number, page: number): Promise<GenericRestWrapper<Record>> {
    return this.http.get(`${this.recordUrl}?title=${title}&result-size=${resultSize}&page=${page}`)
      .toPromise()
      .then(response => response.json() as GenericRestWrapper<Record>)
      .catch(error => this.httpLogger.error(error));
  }

  findByIsbn(isbn: string): Promise<Record> {
    return this.http.get(`${this.recordUrl}?isbn=${isbn}`)
      .toPromise()
      .then(response => response.json().data[0] as Record)
      .catch(error => this.httpLogger.error(error));
  }
}
