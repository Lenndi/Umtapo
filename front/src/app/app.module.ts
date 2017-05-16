import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Http, HttpModule, RequestOptions} from '@angular/http';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {MainComponent} from './main/main.component';
import {TestComponent} from './test/test.component';
import {HttpLoggerService} from '../service/http-logger.service';
import {SetupShelfmarkComponent} from './setup/shelfmark/setup-shelfmark.component';
import {LibraryService} from '../service/library.service';
import {Z3950Service} from '../service/z3950.service';
import {SetupVariousComponent} from './setup/various/setup-various.component';
import {SetupComponent} from './setup/setup.component';
import {NewBorrowerComponent} from './borrower/new-borrower/new-borrower.component';
import {BorrowerService} from '../service/borrower.service';
import {SetupDataService} from '../service/data-binding/setup-data.service';
import 'hammerjs';
import {ItemRegistrationComponent} from './cataloging/item-registration/item-registration.component';
import {ItemSearchComponent} from './cataloging/item-registration/item-search/item-search.component';
import {SearchResultComponent} from './cataloging/item-registration/item-search/search-result/search-result.component';
import {BorrowerSelectionComponent} from './circulation/borrower-selection/borrower-selection.component';
import {MenuComponent} from './main/menu/menu.component';
import {SearchFormComponent} from './cataloging/item-registration/item-search/search-form/search-form.component';
import {TypeaheadModule} from 'ngx-bootstrap/typeahead';
import {DatepickerModule} from 'ngx-bootstrap/datepicker';
import {ModalModule} from 'ngx-bootstrap/modal';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {BorrowerInternalComponent} from './borrower/new-borrower/borrower-internal/borrower-internal.component';
import {BorrowerPersonalComponent} from './borrower/new-borrower/borrower-personal/borrower-personal.component';
import {ItemSaveComponent} from './cataloging/item-registration/item-save/item-save.component';
import {InternalInformationsComponent}
  from './cataloging/item-registration/item-save/internal-informations/internal-informations.component';
import {ItemDetailsComponent} from './cataloging/item-registration/item-save/item-details/item-details.component';
import {CirculationCheckComponent} from './circulation/circulation-check/circulation-check.component';
import {CirculationCheckDetailsComponent}
  from './circulation/circulation-check/circulation-check-details/circulation-check-details.component';
import {CirculationCheckInComponent}
  from './circulation/circulation-check/circulation-check-in/circulation-check-in.component';
import {CirculationCheckOutComponent}
  from './circulation/circulation-check/circulation-check-out/circulation-check-out.component';
import {CirculationComponent} from './circulation/circulation.component';
import {ConditionEnum} from '../enumeration/pipe.enum';
import {LoanService} from '../service/loan.service';
import {BorrowersManagementComponent} from './borrower/borrowers-management/borrowers-management.component';
import {AuthConfig, AuthHttp} from 'angular2-jwt';
import {BorrowerDatatableComponent}
  from './borrower/borrowers-management/borrower-datatable/borrower-datatable.component';
import {BorrowerEditComponent} from './borrower/borrowers-management/borrower-edit/borrower-edit.component';
import {LoginComponent} from './login/login.component';
import {AuthGuard} from '../service/auth-guard.service';
import {LoginService} from '../service/login.service';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';
import {BorrowerDeleteComponent} from './borrower/borrowers-management/borrower-delete/borrower-delete.component';
import { BorrowerRenewalComponent } from './borrower/borrowers-management/borrower-renewal/borrower-renewal.component';
import { ItemManagementComponent } from './cataloging/item-management/item-management.component';
import { ItemDatatableComponent } from './cataloging/item-management/item-datatable/item-datatable.component';
import { ItemEditComponent } from './cataloging/item-management/item-edit/item-edit.component';
import { ExternalLibraryModalComponent }
  from './cataloging/various/external-library-modal/external-library-modal.component';
import { AdministratorSignUpComponent } from './administrator-sign-up/administrator-sign-up.component';
import {UserService} from '../service/user.service';

export function authHttpServiceFactory(http: Http, options: RequestOptions) {
  return new AuthHttp(new AuthConfig({
    headerName: 'Authorization',
    tokenName: 'id_toker',
    tokenGetter: (() => localStorage.getItem('id_token')),
    globalHeaders: [{'Content-Type': 'application/json'}]
  }), http, options);
}

@NgModule({
  declarations: [
    LoginComponent,
    AppComponent,
    MainComponent,
    TestComponent,
    SetupShelfmarkComponent,
    SetupVariousComponent,
    SetupComponent,
    NewBorrowerComponent,
    ItemRegistrationComponent,
    ItemSearchComponent,
    SearchResultComponent,
    BorrowerSelectionComponent,
    MenuComponent,
    SearchFormComponent,
    BorrowerInternalComponent,
    BorrowerPersonalComponent,
    CirculationComponent,
    ItemSaveComponent,
    InternalInformationsComponent,
    ItemDetailsComponent,
    CirculationCheckComponent,
    CirculationCheckDetailsComponent,
    CirculationCheckInComponent,
    CirculationCheckOutComponent,
    ConditionEnum,
    BorrowersManagementComponent,
    BorrowerDatatableComponent,
    BorrowerEditComponent,
    BorrowerDeleteComponent,
    BorrowerRenewalComponent,
    ItemManagementComponent,
    ItemDatatableComponent,
    ItemEditComponent,
    ExternalLibraryModalComponent,
    ItemDatatableComponent,
    AdministratorSignUpComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ModalModule.forRoot(),
    DatepickerModule.forRoot(),
    ToastrModule.forRoot({timeOut: 2000}),
    TypeaheadModule.forRoot(),
    BsDropdownModule.forRoot()
  ],
  providers: [
    {
      provide: AuthHttp,
      useFactory: authHttpServiceFactory,
      deps: [Http, RequestOptions]
    },
    AuthGuard,
    LoginService,
    HttpLoggerService,
    LibraryService,
    Z3950Service,
    SetupDataService,
    BorrowerService,
    LoanService,
    UserService
  ],
  bootstrap: [AppComponent],
  exports: [ConditionEnum],
})
export class AppModule { }
