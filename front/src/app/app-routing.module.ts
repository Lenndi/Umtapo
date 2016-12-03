import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent }          from './main/main.component';
import {TestComponent} from './test/test.component';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      {path: '', redirectTo: 'circulation', pathMatch: 'full'},
      {path: 'circulation', component: TestComponent},
      {path: 'borrowers/new', component: TestComponent},
      {path: 'borrowers/management', component: TestComponent},
      {path: 'cataloging/registration', component: TestComponent},
      {path: 'cataloging/management', component: TestComponent}
    ]
  }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}
