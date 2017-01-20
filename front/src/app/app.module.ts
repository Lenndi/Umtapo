import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
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
import {MaterialModule} from '@angular/material';
import 'hammerjs';
import {ItemRegistrationComponent} from './cataloging/item-registration/item-registration.component';
import {ItemSearchComponent} from './cataloging/item-registration/item-search/item-search.component';
import {SearchResultComponent} from './cataloging/item-registration/item-search/search-result/search-result.component';
import {BorrowerSelectionComponent} from './circulation/borrower-selection/borrower-selection.component';
import {MenuComponent} from './main/menu/menu.component';
import {SearchFormComponent} from './cataloging/item-registration/item-search/search-form/search-form.component';
import {CollapseModule} from 'ng2-bootstrap';
import {BorrowerInternalComponent} from './borrower/new-borrower/borrower-internal/borrower-internal.component';
import {BorrowerPersonalComponent} from './borrower/new-borrower/borrower-personal/borrower-personal.component';
import {CirculationComponent} from './circulation/circulation.component';
import {CirculationCheckComponent} from './circulation/circulation-check/circulation-check.component';
import {CirculationCheckDetailsComponent} from
  './circulation/circulation-check/circulation-check-details/circulation-check-details.component';
import {CirculationCheckInComponent} from
  './circulation/circulation-check/circulation-check-in/circulation-check-in.component';
import {CirculationCheckOutComponent} from
  './circulation/circulation-check/circulation-check-out/circulation-check-out.component';

@NgModule({
  declarations: [
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
    CirculationCheckComponent,
    CirculationCheckDetailsComponent,
    CirculationCheckInComponent,
    CirculationCheckOutComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    CollapseModule.forRoot(),
    MaterialModule.forRoot()
  ],
  providers: [
    HttpLoggerService,
    LibraryService,
    Z3950Service,
    SetupDataService,
    BorrowerService
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
