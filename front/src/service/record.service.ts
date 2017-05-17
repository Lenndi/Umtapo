import {Injectable} from '@angular/core';
import {Headers} from '@angular/http';
import {HttpLoggerService} from './http-logger.service';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import {Record} from '../entity/record/record';
import {Library} from '../entity/library';
import {LibraryService} from './library.service';
import {AuthHttp} from 'angular2-jwt';
import {Page} from '../util/page';

@Injectable()
export class RecordService {
  private recordUrl: string;
  private headers: Headers;

  constructor(private http: AuthHttp, private httpLogger: HttpLoggerService, private libraryService: LibraryService) {
    this.recordUrl = environment.api_url + api.record;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  findByTitle(title: string, resultSize: number, page: number): Promise<Page<Record>> {
    let z3950Param = this.getZ3950Param();

    return this.http.get(`${this.recordUrl}?title=${title}&result-size=${resultSize}&page=${page}${z3950Param}`)
      .toPromise()
      .then(response => response.json() as Page<Record>)
      .catch(error => this.httpLogger.error(error));
  }

  findByIsbn(isbn: string): Promise<Record> {
    let z3950Param = this.getZ3950Param();

    return this.http.get(`${this.recordUrl}?isbn=${isbn}${z3950Param}`)
      .toPromise()
      .then(response => response.json() as Record)
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
