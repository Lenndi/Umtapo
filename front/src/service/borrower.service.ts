import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';
import {Borrower} from '../entity/borrower';

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
}
