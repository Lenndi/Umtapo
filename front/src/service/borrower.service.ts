import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions, Response} from '@angular/http';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';
import {Borrower} from '../entity/borrower';
import {Observable} from 'rxjs';
import {Page} from '../util/page';
import {BorrowerFilter} from './various/borrower-filter';
import {Pageable} from '../util/pageable';

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

  findByNameOrEmail(size: number, page: number, nameOrEmail: string): Observable<Response> {
    return this.http
      .get(`${this.borrowerUrl}?size=${size}&page=${page}&nameOrEmail=${nameOrEmail}`)
      .map(r => {
        if (r.status != 200) {
          return [];
        } else {
          console.log(r.json().content);
          return r.json().content;
        }
      });
  }

  findWithFilters(pageable: Pageable, borrowFilter: BorrowerFilter): Observable<Page<Borrower>> {
    return this.http
      .get(`${this.borrowerUrl}?${pageable.getQueryString()}&${borrowFilter.getQueryString()}`)
      .map(response => response.json() as Page<Borrower>)
      .catch(error => this.httpLogger.error(error));
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
