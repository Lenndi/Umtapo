import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import {Library} from '../entity/library';
import {environment} from '../environments/environment';

import 'rxjs/add/operator/toPromise';
import {backend} from '../config/backend';

@Injectable()
export class LibraryService {
  private libraryUrl;
  private headers;

  constructor(private http: Http) {
    this.libraryUrl = environment.api_url + backend.api.library;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  findLibraries(): Promise<Library[]> {
    return this.http.get(this.libraryUrl)
      .toPromise()
      .then( response => response.json().data as Library[]);
  }
}
