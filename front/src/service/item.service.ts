import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions, Response} from '@angular/http';
import {Item} from '../entity/item';
import {environment} from '../environments/environment';
import {api} from '../config/api';
import 'rxjs/add/operator/toPromise';
import {HttpLoggerService} from './http-logger.service';
import {Observable} from "rxjs";
import {Loan} from "../entity/loan";
import {Borrower} from "../entity/borrower";

@Injectable()
export class ItemService {
  private itemUrl: string;
  private loanUrl: string;
  private headers: Headers;

  constructor(private http: Http, private httpLogger: HttpLoggerService) {
    this.itemUrl = environment.api_url + api.item;
    this.loanUrl = environment.api_url + api.loan;
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
    let patch = {"condition": item.condition};
    return this.http
      .patch(this.itemUrl + "/" + item.id, JSON.stringify(patch), options)
      .toPromise()
      .then(response => response.status)
      .catch(error => this.httpLogger.error(error));
  }

  returnBookItem(id: number): Promise<any> {
    let options = new RequestOptions({headers: this.headers});
    let patch = {"isBorrowed": false};
    return this.http
      .patch(this.itemUrl + "/" + id, JSON.stringify(patch), options)
      .toPromise()
      .then(response => response.status)
      .catch(error => this.httpLogger.error(error));
  }

  findPaginableByContains(size: number, page: number, contains: string, attribute: string): Observable<Item[]> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .get(`http://localhost:8080/items/searchs?size=${size}&page=${page}&contains=${contains}&attribute=${attribute}`, options)
      .map((r: Response) => r.json().content as Item[])
  }

  searchItemByInternalId(itemInternalId: number) : Observable<Response> {
    let options = new RequestOptions({headers: this.headers});

    return this.http.get(this.itemUrl + '/search?internalId=' + itemInternalId, options)
      .map((r: Response) => r)
  }

  patchCheckoutItem(itemId: number): Observable<Response>{
    let options = new RequestOptions({headers: this.headers});
    let patchItem = {"isBorrowed": true};

    return this.http.patch(this.itemUrl + "/" + itemId, patchItem, options)
      .map((r: Response) => r)
  }
}
