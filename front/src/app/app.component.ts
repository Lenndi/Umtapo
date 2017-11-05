import {Component} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'umt-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(private router: Router) {}

  public logout(): void {
    localStorage.removeItem('id_token');
    this.router.navigate(['login']);
  }
}
