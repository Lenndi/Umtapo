import {Component, OnInit} from '@angular/core';
import {Login} from '../../util/login';
import {LoginService} from '../../service/login.service';
import {Router} from '@angular/router';
import {logger} from '../../environments/environment';
import {LibraryService} from '../../service/library.service';
import {ToastrService} from 'ngx-toastr';

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
              private libraryService: LibraryService,
              public toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.loginAdmin.password = 'admin';
    this.loginAdmin.username = 'admin';
    this.loginService.login(this.loginAdmin)
      .then(() => this.router.navigate(['administrator-sign-up']))
      .catch(error => logger.info(`Le mot de passe de l'administrateur a déjà été défini`));
  }

  authenticate() {
    this.loginService.login(this.login)
      .then(() => {
        this.libraryService.findAll()
          .then(libraries => libraries.length < 1 ?
            this.router.navigate(['setup']) : this.router.navigate(['circulation']))
          .catch(error => {
            this.toastr.error('Erreur de connexion avec le serveur', 'Connexion refusée');
            logger.error(error);
          });
      })
      .catch(error => {
        this.toastr.warning('Mauvais mot de passe ou identifiant', 'Connexion refusée');
        logger.error(error);
      });
  }
}
