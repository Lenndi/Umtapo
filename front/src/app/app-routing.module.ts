import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent }          from './main/main.component';
import { TestComponent } from './test/test.component';
import {SetupShelfmarkComponent} from './setup/shelfmark/setup-shelfmark.component';
import {NewBorrowerComponent} from "./new-borrower/new-borrower.component";

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      {path: '', redirectTo: 'circulation', pathMatch: 'full'},
      {path: 'circulation', component: TestComponent},
      {path: 'borrowers/new', component: NewBorrowerComponent},
      {path: 'borrowers/management', component: TestComponent},
      {path: 'cataloging/registration', component: TestComponent},
      {path: 'cataloging/management', component: TestComponent}
    ]
  },
  {path: 'setup/1', component: SetupShelfmarkComponent},
  {path: 'setup/2', component: SetupShelfmarkComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}
