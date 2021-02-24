import { UserModel } from './../models/user-model';
import { AuthentificationService } from './../services/authentification.service';
import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable()
export class JwtInterceptor implements HttpInterceptor {

    constructor(private authenticationService: AuthentificationService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const currentUser: UserModel = this.authenticationService.currentUserValue;

        if (currentUser && currentUser.authToken) {

            req = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${currentUser.authToken}`
                }
            });
        }

        return next.handle(req);
    }

}
