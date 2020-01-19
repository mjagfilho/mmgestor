import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILocal, Local } from 'app/shared/model/local.model';
import { LocalService } from './local.service';
import { LocalComponent } from './local.component';
import { LocalDetailComponent } from './local-detail.component';
import { LocalUpdateComponent } from './local-update.component';

@Injectable({ providedIn: 'root' })
export class LocalResolve implements Resolve<ILocal> {
  constructor(private service: LocalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((local: HttpResponse<Local>) => {
          if (local.body) {
            return of(local.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Local());
  }
}

export const localRoute: Routes = [
  {
    path: '',
    component: LocalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mmGestorApp.local.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocalDetailComponent,
    resolve: {
      local: LocalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmGestorApp.local.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocalUpdateComponent,
    resolve: {
      local: LocalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmGestorApp.local.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocalUpdateComponent,
    resolve: {
      local: LocalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmGestorApp.local.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
