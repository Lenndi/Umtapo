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
    return this.http
      .patch(this.loanUrl, JSON.stringify(loan), options)
      .toPromise()
      .then(response => response.status)
      .catch(error => this.httpLogger.error(error));
  }
}
