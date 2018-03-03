import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainComponent} from './main/main.component';
import {SetupShelfmarkComponent} from './setup/shelfmark/setup-shelfmark.component';
import {SetupVariousComponent} from './setup/various/setup-various.component';
import {SetupComponent} from './setup/setup.component';
import {NewBorrowerComponent} from './borrower/new-borrower/new-borrower.component';
import {ItemSearchComponent} from './cataloging/item-registration/item-search/item-search.component';
import {ItemRegistrationComponent} from './cataloging/item-registration/item-registration.component';
import {CirculationComponent} from './circulation/circulation.component';
import {CirculationCheckComponent} from './circulation/circulation-check/circulation-check.component';
import {BorrowerSelectionComponent} from './circulation/borrower-selection/borrower-selection.component';
import {BorrowerInternalComponent} from './borrower/new-borrower/borrower-internal/borrower-internal.component';
import {BorrowerPersonalComponent} from './borrower/new-borrower/borrower-personal/borrower-personal.component';
import {ItemSaveComponent} from './cataloging/item-registration/item-save/item-save.component';
import {BorrowersManagementComponent} from './borrower/borrowers-management/borrowers-management.component';
import {LoginComponent} from './login/login.component';
import {AuthGuard} from '../service/auth-guard.service';
import {ItemManagementComponent} from './cataloging/item-management/item-management.component';
import {AdministratorSignUpComponent} from './administrator-sign-up/administrator-sign-up.component';
import {LibraryConfigurationComponent} from './library-configuration/library-configuration.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: MainComponent,
    canActivate: [AuthGuard],
    children: [
      {path: '', redirectTo: 'login', pathMatch: 'full'},
      {
        path: 'circulation',
        component: CirculationComponent,
        children: [
          {path: '', redirectTo: 'selection', pathMatch: 'full'},
          {path: 'check', component: CirculationCheckComponent},
          {path: 'selection', component: BorrowerSelectionComponent}
        ]
      },
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
          {path: 'management', component: BorrowersManagementComponent}
        ],
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
              {path: 'save', component: ItemSaveComponent}
            ]
          },
          {path: 'management', component: ItemManagementComponent}
        ],
      },
      {
        path: 'library-configuration',
        component: LibraryConfigurationComponent,
        canActivate: [AuthGuard]
      },
    ]
  },
  {
    path: 'setup',
    component: SetupComponent,
    canActivate: [AuthGuard],
    children: [
      {path: '', redirectTo: '1', pathMatch: 'full'},
      {path: '1', component: SetupShelfmarkComponent},
      {path: '2', component: SetupVariousComponent}
    ]
  },
  {
    path: 'administrator-sign-up',
    component: AdministratorSignUpComponent
  }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}
