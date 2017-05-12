import {Injectable} from '@angular/core';
import {Headers, RequestOptions, Response} from '@angular/http';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';
import {Observable} from 'rxjs';
import {Page} from '../util/page';
import {BorrowerFilter} from './filter/borrower-filter';
import {Pageable} from '../util/pageable';
import {AuthHttp} from 'angular2-jwt';
import {User} from '../entity/record/user';

@Injectable()
export class UserService {
  private userUrl: string;
  private headers: Headers;

  constructor(private http: AuthHttp, private httpLogger: HttpLoggerService) {
    this.userUrl = environment.api_url + api.user;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  findBySso(ssoId: string): Promise<User> {
    let uri;
    let options = new RequestOptions({headers: this.headers});

    return this.http.get(`${this.userUrl + `/ssoId`}?ssoId=${ssoId}`, options)
      .toPromise()
      .then(response => response.json() as User)
      .catch(error => this.httpLogger.error(error));
  }

  update(user: User): Promise<User> {
    let uri;
    let options = new RequestOptions({headers: this.headers});

    return this.http.put(`${this.userUrl}`, JSON.stringify(user), options)
      .toPromise()
      .then(response => response.json() as User)
      .catch(error => this.httpLogger.error(error));
  }
}
