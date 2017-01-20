import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import {HttpLoggerService} from './http-logger.service';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import {Record} from '../entity/record/record';
import {GenericRestWrapper} from '../entity/generic-rest-wrapper';
import {Library} from '../entity/library';
import {LibraryService} from './library.service';

@Injectable()
export class RecordService {
  private recordUrl: string;
  private headers: Headers;

  constructor(private http: Http, private httpLogger: HttpLoggerService, private libraryService: LibraryService) {
    this.recordUrl = environment.api_url + api.record;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  findByTitle(title: string, resultSize: number, page: number): Promise<GenericRestWrapper<Record>> {
    let z3950Param = this.getZ3950Param();

    return this.http.get(`${this.recordUrl}?title=${title}&result-size=${resultSize}&page=${page}${z3950Param}`)
      .toPromise()
      .then(response => response.json() as GenericRestWrapper<Record>)
      .catch(error => this.httpLogger.error(error));
  }

  findByIsbn(isbn: string): Promise<Record> {
    let z3950Param = this.getZ3950Param();

    return this.http.get(`${this.recordUrl}?isbn=${isbn}${z3950Param}`)
      .toPromise()
      .then(response => response.json().data[0] as Record)
      .catch(error => this.httpLogger.error(error));
  }

  private getZ3950Param(): string {
    let library: Library = this.libraryService.findLocally();
    let z3950Param = '';
    if (library) {
      z3950Param += `&z3950=${library.defaultZ3950}`;
    }

    return z3950Param;
  }
}
