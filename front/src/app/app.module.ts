import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { MainComponent } from './main/main.component';
import { TestComponent } from './test/test.component';
import {HttpLoggerService} from '../service/http-logger.service';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    TestComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [HttpLoggerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
