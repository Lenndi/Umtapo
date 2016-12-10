import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import {Library} from '../entity/library';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';

@Injectable()
export class LibraryService {
  private libraryUrl;
  private headers;

  constructor(private http: Http, private httpLogger: HttpLoggerService) {
    this.libraryUrl = environment.api_url + api.library;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  findLibraries(): Promise<Library[]> {
    return this.http.get(this.libraryUrl)
      .toPromise()
      .then(response => response.json().data as Library[])
      .catch(error => this.httpLogger.error(error));
  }

  findLibrary(id: number): Promise<Library> {
    return this.http.get(`${this.libraryUrl}/${id}`)
      .toPromise()
      .then(response => response.json().data as Library)
      .catch(error => this.httpLogger.error(error));
  }
}
