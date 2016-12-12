import {Component, OnInit} from '@angular/core';
import {LibraryService} from '../service/library.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  constructor(private libraryService: LibraryService, private router: Router) {}

  ngOnInit() {
    if (this.libraryService.findLocally()) {
      this.router.navigate(['circulation']);
    } else {
      this.router.navigate(['setup/1']);
    }
  }
}
