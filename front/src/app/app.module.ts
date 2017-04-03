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
import 'hammerjs';
import {ItemRegistrationComponent} from './cataloging/item-registration/item-registration.component';
import {ItemSearchComponent} from './cataloging/item-registration/item-search/item-search.component';
import {SearchResultComponent} from './cataloging/item-registration/item-search/search-result/search-result.component';
import {BorrowerSelectionComponent} from './circulation/borrower-selection/borrower-selection.component';
import {MenuComponent} from './main/menu/menu.component';
import {SearchFormComponent} from './cataloging/item-registration/item-search/search-form/search-form.component';
import {ModalModule, DatepickerModule, DropdownModule} from 'ng2-bootstrap';
import {TypeaheadModule} from 'ng2-bootstrap/typeahead';
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
import {ConditionEnum} from '../entity/enum/pipe.enum';
import {LoanService} from '../service/loan.service';
import {ToastModule} from 'ng2-toastr';
import {BorrowersManagementComponent} from './borrower/borrowers-management/borrowers-management.component';

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
    ItemSaveComponent,
    InternalInformationsComponent,
    ItemDetailsComponent,
    CirculationCheckComponent,
    CirculationCheckDetailsComponent,
    CirculationCheckInComponent,
    CirculationCheckOutComponent,
    ConditionEnum,
    BorrowersManagementComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    ModalModule.forRoot(),
    DatepickerModule.forRoot(),
    ToastModule.forRoot(),
    TypeaheadModule.forRoot(),
    DropdownModule.forRoot()
  ],
  providers: [
    HttpLoggerService,
    LibraryService,
    Z3950Service,
    SetupDataService,
    BorrowerService,
    LoanService
  ],
  bootstrap: [AppComponent],
  exports: [ConditionEnum],
})
export class AppModule { }
