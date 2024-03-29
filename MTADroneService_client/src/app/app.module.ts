import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MarkdownModule } from 'ngx-markdown';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { ServicesComponent } from './components/services/services.component';
import { HistoryComponent } from './components/history/history.component';
import { LoginComponent } from './components/login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { JwtInterceptor } from 'src/app/classes/jwt-interceptor';
import { AboutComponent } from './components/about/about.component';
import { MissionsComponent } from './components/missions/missions.component';
import { ContactComponent } from './components/contact/contact.component'
import { AgmCoreModule } from '@agm/core';
import { ReviewsComponent } from './components/reviews/reviews.component';
import { NgImageSliderModule } from 'ng-image-slider';
import { NgxPageScrollCoreModule } from 'ngx-page-scroll-core';



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    ServicesComponent,
    HistoryComponent,
    LoginComponent,
    AboutComponent,
    MissionsComponent,
    ContactComponent,
    ReviewsComponent,
  ],
  imports: [
    NgImageSliderModule,
    BrowserModule,
    AppRoutingModule,
    MarkdownModule.forRoot(),
    ReactiveFormsModule,
    HttpClientModule,
    NgxPageScrollCoreModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBTUZQAHPsGITuBpKljuP3pN8Qgk6E_lXg',
      libraries: ['places']
    }), 
  ],
  providers: [CookieService,{provide:HTTP_INTERCEPTORS,useClass:JwtInterceptor,multi:true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
