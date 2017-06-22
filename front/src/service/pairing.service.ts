import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions, Response} from '@angular/http';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';
import {Borrower} from '../entity/borrower';
import {Observable} from 'rxjs';
import {Page} from '../util/page';
import {BorrowerFilter} from './filter/borrower-filter';
import {Pageable} from '../util/pageable';
import {Pairing} from '../util/pairing';
import {AuthHttp} from 'angular2-jwt';

@Injectable()
export class PairingService {
  private pairingUrl: string;
  private headers: Headers;

  constructor(private http: AuthHttp, private httpLogger: HttpLoggerService) {
    this.pairingUrl = environment.api_url + api.pairing;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  pairingCard(pairing: Pairing): Promise<any> {

    let options = new RequestOptions({headers: this.headers});

    return this.http
      .post(`${this.pairingUrl}/borrower`, pairing, options)
      .toPromise();
  }

  pairingItem(pairing: Pairing): Promise<any> {

    let options = new RequestOptions({headers: this.headers});

    return this.http
      .post(`${this.pairingUrl}/item`, pairing, options)
      .toPromise();
  }

}
