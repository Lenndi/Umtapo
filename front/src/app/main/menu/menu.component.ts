import {Component} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'umt-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent {
  constructor(private router: Router) {}

  isOpen(routeCategory: string) {
    return this.router.url.includes(routeCategory);
  }
}
