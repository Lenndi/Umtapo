import { Injectable } from '@angular/core';
import {Headers} from "@angular/http";

@Injectable()
export class BorrowerService {
  private libraryUrl;
  private headers;

  constructor(private http: Http, private httpLogger: HttpLoggerService) {
    this.libraryUrl = environment.api_url + api.library;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }


}
