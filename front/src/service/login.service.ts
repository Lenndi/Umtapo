import {Injectable} from '@angular/core';
import {api} from '../config/api';
import {Http, Headers, RequestOptions} from '@angular/http';
import {HttpLoggerService} from './http-logger.service';
import {environment, logger} from '../environments/environment';
import {Login} from '../util/login';
import {Token} from '../util/token';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class LoginService {
  private loginUrl: string;
  private headers: Headers;
  private token: Token;

  constructor(public http: Http, private httpLogger: HttpLoggerService) {
    this.loginUrl = environment.api_url + api.login;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  login(login: Login): Promise<any> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .post(this.loginUrl, JSON.stringify(login), options)
      .toPromise()
      .then(
        data => {
          this.token = data.json();
          localStorage.setItem('id_token', this.token.Authorization);
        })
      .catch(error => logger.error(error));
  }
}

