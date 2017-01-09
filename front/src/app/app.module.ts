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
import {NewBorrowerComponent} from './new-borrower/new-borrower.component';
import {BorrowerService} from '../service/borrower.service';
import {SetupDataService} from '../service/data-binding/setup-data.service';
import {MaterialModule} from '@angular/material';
import 'hammerjs';
import {ItemRegistrationComponent} from './cataloging/item-registration/item-registration.component';
import {ItemSearchComponent} from './cataloging/item-registration/item-search/item-search.component';
import {SearchResultComponent} from './cataloging/item-registration/item-search/search-result/search-result.component';

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
    SearchResultComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
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
