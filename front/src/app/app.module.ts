import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { MainComponent } from './main/main.component';
import { TestComponent } from './test/test.component';
import {HttpLoggerService} from '../service/http-logger.service';
import { SetupShelfmarkComponent } from './setup/shelfmark/setup-shelfmark.component';
import {LibraryService} from '../service/library.service';
import {Z3950Service} from '../service/z3950.service';
import {MaterializeModule} from 'angular2-materialize';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    TestComponent,
    SetupShelfmarkComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterializeModule
  ],
  providers: [
    HttpLoggerService,
    LibraryService,
    Z3950Service
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
