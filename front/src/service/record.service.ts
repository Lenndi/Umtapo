import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptionsArgs, RequestOptions} from '@angular/http';
import {HttpLoggerService} from './http-logger.service';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import {Record} from '../entity/record/record';

@Injectable()
export class RecordService {
  private recordUrl: string;
  private headers: Headers;

  constructor(private http: Http, private httpLogger: HttpLoggerService) {
    this.recordUrl = environment.api_url + api.record;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  /**
   *
   * @param title
   * @param resultSize
   * @param page
   * @returns Promise<records: Record[], page: number, totalPage: number>
   */
  findByTitle(title: string, resultSize: number, page: number): Promise<[Record[], number, number]> {
    let options: RequestOptionsArgs = new RequestOptions();
    return this.http.get(`${this.recordUrl}?title=${title}&result-size=${resultSize}&page=${page}`)
      .toPromise()
      .then(response => {
        let body = response.json();
        return [body.data, body.page, body.totalPage];
      })
      .catch(error => this.httpLogger.error(error));
  }

  findByIsbn(isbn: string): Promise<Record> {
    return this.http.get(`this.recordUrl?isbn=${isbn}`)
      .toPromise()
      .then(response => response.json().data[0] as Record)
      .catch(error => this.httpLogger.error(error));
  }
}
