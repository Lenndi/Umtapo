
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivate } from '@angular/router';
import { tokenNotExpired } from 'angular2-jwt';

@Injectable()
export class AuthGuard implements CanActivate {

  loggedIn() {
    return tokenNotExpired();
  }

  constructor(private router: Router) {}

  canActivate() {
    console.log(this.loggedIn());
    if(this.loggedIn()) {
      return true;
    } else {
      this.router.navigate(['login']);
      return false;
    }
  }
}
