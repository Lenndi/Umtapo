import {Injectable} from '@angular/core';
import {Headers} from '@angular/http';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';
import {Z3950} from '../entity/z3950';
import {AuthHttp} from 'angular2-jwt';

@Injectable()
export class Z3950Service {
  private z3950Url: string;
  private headers: Headers;

  constructor(private http: AuthHttp, private httpLogger: HttpLoggerService) {
    this.z3950Url = environment.api_url + api.z3950;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  findAll(): Promise<Z3950[]> {
    return this.http.get(this.z3950Url)
      .toPromise()
      .then(response => response.json() as Z3950[])
      .catch(error => this.httpLogger.error(error));
  }
}
