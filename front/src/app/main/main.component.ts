import { Component, AfterViewInit } from '@angular/core';
declare const $: any;

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent  implements AfterViewInit {
  ngAfterViewInit(): void {
    $('.button-collapse').sideNav();
  }

  hideMenu(): void {
    $('.button-collapse').sideNav('hide');
  }
}
