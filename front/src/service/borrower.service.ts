import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import {Library} from '../entity/library';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';

@Injectable()
export class BorrowerService {
  private borrowerUrl;
  private headers;

  constructor(private http: Http, private httpLogger: HttpLoggerService) {
    this.borrowerUrl = environment.api_url + api.borrower;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }


}
