import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainComponent} from './main/main.component';
import {TestComponent} from './test/test.component';
import {SetupShelfmarkComponent} from './setup/shelfmark/setup-shelfmark.component';
import {SetupVariousComponent} from './setup/various/setup-various.component';
import {SetupComponent} from './setup/setup.component';
import {AppComponent} from './app.component';
import {NewBorrowerComponent} from './borrower/new-borrower/new-borrower.component';
import {ItemSearchComponent} from './cataloging/item-registration/item-search/item-search.component';
import {ItemRegistrationComponent} from './cataloging/item-registration/item-registration.component';
import {CirculationComponent} from './circulation/circulation.component';
import {CirculationCheckComponent} from './circulation/circulation-check/circulation-check.component';
import {BorrowerSelectionComponent} from './circulation/borrower-selection/borrower-selection.component';
import {BorrowerInternalComponent} from './borrower/new-borrower/borrower-internal/borrower-internal.component';
import {BorrowerPersonalComponent} from './borrower/new-borrower/borrower-personal/borrower-personal.component';

const routes: Routes = [
  {
    path: '',
    component: AppComponent
  },
  {
    path: '',
    component: MainComponent,
    children: [
      {path: '', redirectTo: 'circulation', pathMatch: 'full'},
      {
        path: 'circulation',
        component: CirculationComponent,
        children: [
          {path: '', redirectTo: 'selection', pathMatch: 'full'},
          {path: 'check/:id', component: CirculationCheckComponent},
          {path: 'selection', component: BorrowerSelectionComponent}
        ]
      },
      {path: 'borrowers/new', component: NewBorrowerComponent},
      {path: 'borrowers/management', component: TestComponent},
      {
        path: 'borrowers',
        children: [
          {
            path: 'new',
            component: NewBorrowerComponent,
            children: [
              {path: '', redirectTo: '1', pathMatch: 'full'},
              {path: '1', component: BorrowerPersonalComponent},
              {path: '2', component: BorrowerInternalComponent}
            ]
          },
          {path: 'management', component: TestComponent}
        ]
      },
      {
        path: 'cataloging',
        children: [
          {
            path: 'registration',
            component: ItemRegistrationComponent,
            children: [
              {path: '', redirectTo: 'search', pathMatch: 'full'},
              {path: 'search', component: ItemSearchComponent},
              {path: 'save', component: TestComponent}
            ]
          },
          {path: 'management', component: TestComponent}
        ]
      },
    ]
  },
  {
    path: 'setup',
    component: SetupComponent,
    children: [
      {path: '', redirectTo: '1', pathMatch: 'full'},
      {path: '1', component: SetupShelfmarkComponent},
      {path: '2', component: SetupVariousComponent}
    ]
  }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}
