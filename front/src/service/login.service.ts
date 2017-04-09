import {AuthHttp, JwtHelper} from 'angular2-jwt';
import {Injectable} from "@angular/core";
import {api} from "../config/api";
import {Http, Headers} from "@angular/http";
import {HttpLoggerService} from "./http-logger.service";
import {environment} from "../environments/environment";
import {Login} from "../app/dto/login";
import {Token} from "../app/dto/token";

@Injectable()
export class LoginService {
  private loginUrl: string;
  private headers: Headers;
  private token: Token;

  jwtHelper: JwtHelper = new JwtHelper();


  constructor(public http: Http, private httpLogger: HttpLoggerService) {
    this.loginUrl = environment.api_url + api.login;
    this.headers = new Headers({'Content-Type': 'application/json'});
  }

  login(login: Login): any {
    return this.http.post(this.loginUrl, login, this.headers)
      .map(
        data => {
          this.token = data.json();
          sessionStorage.setItem('id_token', this.token.Authorization);
          console.log(this.jwtHelper.decodeToken(this.token.Authorization));
          console.log(this.jwtHelper.getTokenExpirationDate(this.token.Authorization));
          console.log(this.jwtHelper.isTokenExpired(this.token.Authorization));
        });
  }
}

