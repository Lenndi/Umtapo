import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions, Response} from '@angular/http';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';
import {Borrower} from '../entity/borrower';
import {Observable} from 'rxjs';

@Injectable()
export class BorrowerService {
  private borrowerUrl: string;
  private headers: Headers;

  constructor(private http: Http, private httpLogger: HttpLoggerService) {
    this.borrowerUrl = environment.api_url + api.borrower;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  save(borrower: Borrower): Promise<Borrower> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .post(this.borrowerUrl, JSON.stringify(borrower), options)
      .toPromise()
      .then(response => response.json() as Borrower)
      .catch(error => this.httpLogger.error(error));
  }

  findAll(jsonViewResolver?: string): Promise<Borrower[]> {
    let uri;
    if (jsonViewResolver != null) {
      uri = this.borrowerUrl + jsonViewResolver;
    } else {
      uri = this.borrowerUrl;
    }
    return this.http.get(uri)
      .toPromise()
      .then(response => response.json() as Borrower[])
      .catch(error => this.httpLogger.error(error));
  }

  findPaginableByName(size: number, page: number, contains: string): Observable<Borrower[]> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .get(`http://localhost:8080/borrowers/searchs?size=${size}&page=${page}&contains=${contains}&attribute=name`,
        options)
      .map((r: Response) => r.json().content as Borrower[]);

  }

  find(id: number, jsonViewResolver?: string): Promise<Borrower> {
    let uri;
    let options = new RequestOptions({headers: this.headers});
    if (jsonViewResolver != null) {
      uri = `${this.borrowerUrl}/${id}` + jsonViewResolver;
    } else {
      uri = `${this.borrowerUrl}/${id}`;
    }
    return this.http.get(uri, options)
      .toPromise()
      .then(response => response.json() as Borrower)
      .catch(error => this.httpLogger.error(error));
  }
}
