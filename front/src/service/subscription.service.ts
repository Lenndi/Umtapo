import {Http, Headers, RequestOptions} from '@angular/http';
import {Injectable} from '@angular/core';
import {HttpLoggerService} from './http-logger.service';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import {Subscription} from '../entity/subscription';

@Injectable()
export class SubscriptionService {
  private subscriptionUrl: string;
  private headers: Headers;

  constructor(private http: Http, private httpLogger: HttpLoggerService) {
    this.subscriptionUrl = environment.api_url + api.subscription;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  save(subscription: Subscription): Promise<Subscription> {
    let options = new RequestOptions({headers: this.headers});

    return this.http
      .post(this.subscriptionUrl, JSON.stringify(subscription), options)
      .toPromise()
      .then(response => response.json() as Subscription)
      .catch(error => this.httpLogger.error(error));
  }
}
