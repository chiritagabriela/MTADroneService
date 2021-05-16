import { Injectable } from '@angular/core';
import { Observable, of, empty, BehaviorSubject } from 'rxjs';
import { UserModel } from '../models/user-model';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})

export class AuthentificationService {

  static USER_INFO = 'USER_INFO';
  private userInfoSubject:BehaviorSubject<UserModel>;
  public currentUser$:Observable<UserModel>;
  
  constructor(private httpClient:HttpClient, private cookieService:CookieService ) { 

    const jsonString = this.cookieService.get(AuthentificationService.USER_INFO);
    if(jsonString === ""){
      this.userInfoSubject = new BehaviorSubject<UserModel>(null as any);
    }
    else{
      this.userInfoSubject = new BehaviorSubject<UserModel>(JSON.parse(jsonString));
    }
    
    this.currentUser$ = this.userInfoSubject.asObservable();
  }

  login(username: string, password:string): Observable<UserModel>{

    const url = `https://romtadroneservice.mooo.com/user/login`;
    return this.httpClient.post<UserModel>(url,{username,password})
    .pipe(map(userModel =>{

      //create cookie with jwtToken
      this.cookieService.set(AuthentificationService.USER_INFO,JSON.stringify(userModel));

      //notify the others
      this.userInfoSubject.next(userModel);

      return userModel;
    })
    );
  }
 
  logout(){
    this.cookieService.delete(AuthentificationService.USER_INFO);
    this.userInfoSubject.next(null as any);
  }

  public get currentUserValue(){
    return this.userInfoSubject.value;
  }

  register(formValue:any): Observable<UserModel>{

    const url = `https://romtadroneservice.mooo.com/user/create`;
    return this.httpClient.post<UserModel>(url,formValue)
    .pipe(map(userModel =>{

      //create cookie with jwtToken
      this.cookieService.set(AuthentificationService.USER_INFO,JSON.stringify(userModel));

      //notify the others
      this.userInfoSubject.next(userModel);

      return userModel;
    })
    );
  }
}
