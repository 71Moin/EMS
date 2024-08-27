import { Injectable, inject } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class authInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const userName = localStorage.getItem('userName') ?? '';
    const authorization=localStorage.getItem('authorization')??'';
    const authReq = req.clone({
      headers: req.headers.set('Content-Type', 'application/json')
      .set('userName',userName) 
      .set('authorization',authorization)
    });
    // const clonedRequest = req.clone({
    //   headers: new HttpHeaders({
    //     'Content-Type':  'application/json',
    //     'userName': `${userName}`
    //   })
    // });

    return next.handle(authReq);
  }
}
