import {Http, Headers, RequestOptions} from "@angular/http";
import {Injectable} from "@angular/core";
import {HttpLoggerService} from "./http-logger.service";
import {environment} from '../environments/environment';
import {api} from "../config/api";
import {Loan} from "../entity/loan";

@Injectable()
export class LoanService {
  private loanUrl: string;
  private headers: Headers;

  constructor(private http: Http, private httpLogger: HttpLoggerService) {
    this.loanUrl = environment.api_url + api.loan;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  saveEnd(loan: Loan): Promise<any> {
    let options = new RequestOptions({headers: this.headers});
    let patch = {"end" : loan.end};
    return this.http
      .patch(this.loanUrl + "/" + loan.id, JSON.stringify(patch), options)
      .toPromise()
      .then(response => response.status)
      .catch(error => this.httpLogger.error(error));
  }

  findAllDtoByBorrowerIdAndReturned(borrowerId: number): Promise<Loan[]> {
    let options = new RequestOptions({headers: this.headers});
    return this.http
      .get(this.loanUrl + "?id=" + borrowerId, options)
      .toPromise()
      .then(response => response.json() as Loan[])
      .catch(error => this.httpLogger.error(error));
  }

  returnBookLoan(id: number): Promise<any> {
    let options = new RequestOptions({headers: this.headers});
    let patch = {"returned" : true};
    return this.http
      .patch(this.loanUrl + "/" + id, JSON.stringify(patch), options)
      .toPromise()
      .then(response => response.status)
      .catch(error => this.httpLogger.error(error));
  }
}
