import { Component, OnInit } from '@angular/core';
import {Login} from "../dto/login";
import { AuthHttp } from 'angular2-jwt';
import {LoginService} from "../../service/login.service";
import {Router} from '@angular/router';

@Component({
  selector: 'umt-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  login: Login = new Login;

  constructor(
    private loginService: LoginService,
    private router: Router
  ) { }

  ngOnInit() {
  }

  authenticate(){
    this.loginService.login(this.login).subscribe(
      data => { this.router.navigate(['circulation']);
      },
      err => console.log("nop"),
      () => console.log("finish")
    );
  }

}
