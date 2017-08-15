import {Component, OnInit} from '@angular/core';
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
export class LoginComponent implements OnInit {

  login: Login = new Login;
  loginAdmin: Login = new Login;

  constructor(private loginService: LoginService,
              private router: Router,
              private libraryService: LibraryService) {
  }

  ngOnInit(): void {
    this.loginAdmin.password = 'admin';
    this.loginAdmin.username = 'admin';
    this.loginService.login(this.loginAdmin)
      .then(data => {
        this.router.navigate(['administrator-sign-up']);
      })
      .catch(error => logger.info(`Le mot de passe de l'administrateur a déjà été défini`));
  }

  authenticate() {
    this.loginService.login(this.login)
      .then(data => {
        if (this.libraryService.findLocally()) {
          this.router.navigate(['circulation']);
        } else {
          this.router.navigate(['setup']);
        }
      })
      .catch(error => {
        logger.error(error);
      });
  }
}
