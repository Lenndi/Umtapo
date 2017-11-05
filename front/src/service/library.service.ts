import {Injectable} from '@angular/core';
import {Headers, RequestOptions} from '@angular/http';
import {Library} from '../entity/library';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';
import {AuthHttp} from 'angular2-jwt';

@Injectable()
export class LibraryService {
  private libraryUrl: string;
  private headers: Headers;

  constructor(private http: AuthHttp, private httpLogger: HttpLoggerService) {
    this.libraryUrl = environment.api_url + api.library;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  findAll(): Promise<Library[]> {
    return this.http.get(this.libraryUrl)
      .toPromise()
      .then(response => response.json() as Library[])
      .catch(error => this.httpLogger.error(error));
  }

  find(id: number): Promise<Library> {
    return this.http.get(`${this.libraryUrl}/${id}`)
      .toPromise()
      .then(response => response.json() as Library)
      .catch(error => this.httpLogger.error(error));
  }

  findExternalLibraries(): Promise<Library[]> {
    return this.http.get(`${this.libraryUrl}?external=true`)
      .toPromise()
      .then(response => response.json() as Library[])
      .catch(error => this.httpLogger.error(error));
  }

  findPartnerLibraries(): Promise<Library[]> {
    return this.http.get(`${this.libraryUrl}?external=false`)
      .toPromise()
      .then(response => response.json() as Library[])
      .catch(error => this.httpLogger.error(error));
  }

  save(library: Library): Promise<Library> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .post(`${this.libraryUrl}/partner`, JSON.stringify(library), options)
      .toPromise()
      .then(response => response.json() as Library)
      .catch(error => this.httpLogger.error(error));
  }

  update(library: Library): Promise<Library> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .put(`${this.libraryUrl}/partner`, JSON.stringify(library), options)
      .toPromise()
      .then(response => response.json() as Library)
      .catch(error => this.httpLogger.error(error));
  }

  saveExternal(library: Library): Promise<Library> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .post(`${this.libraryUrl}/external`, JSON.stringify(library), options)
      .toPromise()
      .then(response => response.json() as Library)
      .catch(error => this.httpLogger.error(error));
  }

  saveLocally(library: Library): void {
    localStorage.setItem('library', JSON.stringify(library));
  }

  findLocally(): Library {
    let libraryStr: string = localStorage.getItem('library');
    if (!libraryStr) {
      return null;
    } else {
      return JSON.parse(libraryStr) as Library;
    }
  }
}
