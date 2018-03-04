import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../service/user.service';
import {User} from '../../entity/record/user';
import {Router} from '@angular/router';
import {logger} from '../../environments/environment';
import {ToastrService} from 'ngx-toastr';
import {LibraryService} from '../../service/library.service';
import {CustomMap} from '../../enumeration/custom-map';
import {ApplicationCodeEnum} from '../../enumeration/application-code';

@Component({
  selector: 'umt-administrator-sign-up',
  templateUrl: './administrator-sign-up.component.html',
  styleUrls: ['./administrator-sign-up.component.scss']
})
export class AdministratorSignUpComponent {

  ssoId: FormControl;
  password: FormControl;
  firstName: FormControl;
  lastName: FormControl;
  email: FormControl;
  ssoIdAdmin = 'admin';
  user: User;
  form: FormGroup;
  conditionEnum: CustomMap;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private router: Router,
              private libraryService: LibraryService,
              private toastr: ToastrService) {
    this.conditionEnum = ApplicationCodeEnum;
    userService.findBySso(this.ssoIdAdmin)
      .then(data => {
        this.user = data;
        this.ssoId = new FormControl(this.user != null ? this.user.ssoId : '', Validators.required);
        this.password = new FormControl('', Validators.required);
        this.firstName = new FormControl(this.user != null ? this.user.firstName : '', Validators.required);
        this.lastName = new FormControl(this.user != null ? this.user.lastName : '', Validators.required);
        this.email = new FormControl(this.user != null ? this.user.email : '', Validators.required);

        this.form = this.formBuilder.group({
          'ssoId': this.ssoId,
          'password': this.password,
          'firstName': this.firstName,
          'lastName': this.lastName,
          'email': this.email
        });

      })
      .catch(err => logger.error(`Le composant administrator sign up ne trouve pas l'utilisateur admin`));
  }

  onSubmit(value: any): void {
    if (this.form.valid) {
      value.id = this.user.id;
      this.userService.update(value)
        .then(() => {
          if (this.libraryService.findLocally()) {
            this.router.navigate(['circulation']);
          } else {
            this.router.navigate(['setup']);
          }
        })
        .catch(err => {
          if (err.status = ApplicationCodeEnum.get('LOGIN_AND_PASSWORD_ARE_EQUALS')) {
            this.toastr.error(`Le mot de passe doit être différent de l'identifiant`, 'Problème');
          }
          logger.error(`Problème lors de la mise à jour des informations de l'administrateur` + err);
        });
    }
  }
}
