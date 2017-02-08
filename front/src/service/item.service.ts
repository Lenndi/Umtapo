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
    let patch = {"loanable": true};
    return this.http
      .patch(this.itemUrl + "/" + id, JSON.stringify(patch), options)
      .toPromise()
      .then(response => response.status)
      .catch(error => this.httpLogger.error(error));
  }

  findPaginableBySerialNumber(size: number, page: number, contains: string): Observable<Item[]> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .get(`http://localhost:8080/items/searchs?size=${size}&page=${page}&contains=${contains}&attribute=barCode`, options)
      .map((r: Response) => r.json().content as Item[]);

  }

  setLoanAndItemCheckOut(itemInternalId: number, borrowerId: number) {
    let options = new RequestOptions({headers: this.headers});
    let loan: Loan = new Loan;
    let loanPost: Loan = new Loan;
    let item: Item = new Item;
    let itemLend: Item = new Item;
    let patchItem = {"isBorrowed": true};

    loan.borrower = new Borrower;
    loan.item = new Item;
    loan.borrower.id = borrowerId;
    console.log(loan);
    this.http.get(this.itemUrl + '/search?internalId=' + itemInternalId, options).map((response: Response) => {
      item = response.json();
      loan.item.id = item.id;
      loan.returned = false;
      loanPost = loan;
      return loan;
    })
      .flatMap((loan) => this.http.patch(this.itemUrl + "/" + loan.item.id, patchItem, options)).map((response: Response) => {
      item = response.json();
      return item;
    })
      .flatMap((loan) => this.http.post(this.loanUrl, loanPost, options)).map((response: Response) => {
      loan = response.json();
      return loan;
    })
      .subscribe(response => loan = response);
  }
}
