import {Component} from '@angular/core';
import {Login} from '../../util/login';
import {LoginService} from '../../service/login.service';
import {Router} from '@angular/router';
import {logger} from '../../environments/environment';
import {LibraryService} from '../../service/library.service';

@Component({
  selector: 'umt-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  login: Login = new Login;

  constructor(
    private loginService: LoginService,
    private router: Router,
    private libraryService: LibraryService
  ) { }

  authenticate() {
    this.loginService.login(this.login)
      .then(data => {
        if (this.libraryService.findLocally()) {
          this.router.navigate(['circulation']);
        } else {
          this.router.navigate(['setup']);
        }
      })
      .catch(error => logger.error(error));
  }
}
