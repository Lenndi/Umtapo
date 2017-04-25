
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivate } from '@angular/router';
import { tokenNotExpired } from 'angular2-jwt';

@Injectable()
export class AuthGuard implements CanActivate {

  static loggedIn() {
    console.log('LoggedIn ' + tokenNotExpired('id_token'));
    return tokenNotExpired('id_token');
  }

  constructor(private router: Router) {}

  canActivate() {
    if (AuthGuard.loggedIn()) {
      return true;
    } else {
      this.router.navigate(['login']);
      return false;
    }
  }
}
