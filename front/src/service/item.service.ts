import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import {Item} from '../entity/item';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';

@Injectable()
export class ItemService {
  private itemUrl: string;
  private headers: Headers;

  constructor(private http: Http, private httpLogger: HttpLoggerService) {
    this.itemUrl = environment.api_url + api.item;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  find(id: number): Promise<Item> {
    return this.http.get(`${this.itemUrl}/${id}`)
      .toPromise()
      .then(response => response.json() as Item)
      .catch(error => this.httpLogger.error(error));
  }

  save(item: Item): Promise<Item> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .post(this.itemUrl, JSON.stringify(item), options)
      .toPromise()
      .then(response => response.json() as Item)
      .catch(error => this.httpLogger.error(error));
  }

  saveCondition(item: Item): Promise<number> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .patch(this.itemUrl, JSON.stringify(item), options)
      .toPromise()
      .then(response => response.status)
      .catch(error => this.httpLogger.error(error));
  }

  saveInternalId(item: Item): Promise<number> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .patch(this.itemUrl, JSON.stringify(item), options)
      .toPromise()
      .then(response => response.status)
      .catch(error => this.httpLogger.error(error));
  }
}
