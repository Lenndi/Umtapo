import { Component, OnInit } from '@angular/core';
import {FormControl, FormBuilder, Validators, FormGroup} from '@angular/forms';
import {UserService} from '../../service/user.service';
import {User} from '../../entity/record/user';
import {Router} from '@angular/router';
import {logger} from '../../environments/environment';
import {LibraryService} from '../../service/library.service';

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

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private libraryService: LibraryService
  ) {
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
        .then(data => {
          if (this.libraryService.findLocally()) {
            this.router.navigate(['circulation']);
          } else {
            this.router.navigate(['setup']);
          }
        })
        .catch(err => logger.error(`Problème lors de la mise à jour des informations de l'administrateur` + err));
    }
  }
}
