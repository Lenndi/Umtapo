import {Component} from '@angular/core';
import {Login} from '../../util/login';
import {LoginService} from '../../service/login.service';
import {Router} from '@angular/router';
import {logger} from '../../environments/environment';

@Component({
  selector: 'umt-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  login: Login = new Login;

  constructor(
    private loginService: LoginService,
    private router: Router
  ) { }

  authenticate() {
    this.loginService.login(this.login)
      .then(data => this.router.navigate(['circulation']))
      .catch(error => logger.error(error));
  }
}
