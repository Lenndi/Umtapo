import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import {Library} from '../entity/library';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';

@Injectable()
export class LibraryService {
  private libraryUrl: string;
  private headers: Headers;

  constructor(private http: Http, private httpLogger: HttpLoggerService) {
    this.libraryUrl = environment.api_url + api.library;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  findAll(): Promise<Library[]> {
    return this.http.get(this.libraryUrl)
      .toPromise()
      .then(response => response.json().data as Library[])
      .catch(error => this.httpLogger.error(error));
  }

  find(id: number): Promise<Library> {
    return this.http.get(`${this.libraryUrl}/${id}`)
      .toPromise()
      .then(response => response.json().data as Library)
      .catch(error => this.httpLogger.error(error));
  }

  saveLocally(library: Library): void {
    localStorage.setItem('library', JSON.stringify(library));
  }

  findLocally(): Library {
    let libraryStr: string = localStorage.getItem('library');
    if (!libraryStr) {
      throw {'name': 'StorageException', 'message': 'Library not found'};
    }

    return JSON.parse(libraryStr).data as Library;
  }
}
