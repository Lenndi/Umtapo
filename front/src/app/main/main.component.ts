import { Component, AfterViewInit } from '@angular/core';
import {Router} from '@angular/router';
declare const $: any;

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent  implements AfterViewInit {
  constructor(private router: Router) {}

  ngAfterViewInit(): void {
    $('.button-collapse').sideNav();
  }

  hideMenu(): void {
    $('.button-collapse').sideNav('hide');
  }

  isOpen(routeCategory: string) {
    return this.router.url.includes(routeCategory);
  }
}
