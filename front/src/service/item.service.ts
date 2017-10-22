import {Injectable} from '@angular/core';
import {Headers, RequestOptions, Response} from '@angular/http';
import {Item} from '../entity/item';
import {environment, logger} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';
import {Observable} from 'rxjs';
import {AuthHttp} from 'angular2-jwt';
import {Pageable} from '../util/pageable';
import {ItemFilter} from './filter/item-filter';
import {Page} from '../util/page';

@Injectable()
export class ItemService {
  private itemUrl: string;
  private loanUrl: string;
  private headers: Headers;

  constructor(private http: AuthHttp, private httpLogger: HttpLoggerService) {
    this.itemUrl = environment.api_url + api.item;
    this.loanUrl = environment.api_url + api.loan;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  find(id: number): Promise<Item> {
    return this.http.get(`${this.itemUrl}/${id}`)
      .toPromise()
      .then(response => {
        return response.json() as Item;
      })
      .catch(error => {
        this.httpLogger.error(error);
      });
  }

  save(item: Item): Promise<Item> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .post(this.itemUrl, JSON.stringify(item), options)
      .toPromise()
      .then(response => response.json() as Item)
      .catch(error => this.httpLogger.error(error));
  }

  update(item: Item): Promise<Item> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .put(this.itemUrl, JSON.stringify(item), options)
      .toPromise()
      .then(response => response.json() as Item)
      .catch(error => this.httpLogger.error(error));
  }

  saveCondition(item: Item): Promise<number> {
    let options = new RequestOptions({headers: this.headers});
    let patch = {'condition': item.condition};
    return this.http
      .patch(`${this.itemUrl}/${item.id}`, JSON.stringify(patch), options)
      .toPromise()
      .then(response => response.status)
      .catch(error => this.httpLogger.error(error));
  }

  returnBookItem(id: number): Promise<any> {
    let options = new RequestOptions({headers: this.headers});
    let patch = {'borrowed': false};

    return this.http
      .patch(`${this.itemUrl}/${id}`, patch, options)
      .toPromise()
      .then(response => response.status)
      .catch(error => this.httpLogger.error(error));
  }

  findWithFilters(pageable: Pageable, itemFilter: ItemFilter): Observable<Page<Item>> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .get(`${this.itemUrl}/searchs?${pageable.getQueryString()}&${itemFilter.getQueryString()}`, options)
      .map(response => {
        if (response.status != 200) {
          return [];
        } else {
          logger.debug('ItemService.findWithFilters', response.json());
          return response.json() as Page<Item>;
        }
      });
  }

  searchItemByInternalId(itemInternalId: number): Observable<Response> {
    let options = new RequestOptions({headers: this.headers});

    return this.http.get(this.itemUrl + '/search?internalId=' + itemInternalId, options)
      .map((r: Response) => r);
  }
}
