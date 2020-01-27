import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoLocal, TipoLocal } from 'app/shared/model/tipo-local.model';
import { TipoLocalService } from './tipo-local.service';
import { TipoLocalComponent } from './tipo-local.component';
import { TipoLocalDetailComponent } from './tipo-local-detail.component';
import { TipoLocalUpdateComponent } from './tipo-local-update.component';

@Injectable({ providedIn: 'root' })
export class TipoLocalResolve implements Resolve<ITipoLocal> {
  constructor(private service: TipoLocalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoLocal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoLocal: HttpResponse<TipoLocal>) => {
          if (tipoLocal.body) {
            return of(tipoLocal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoLocal());
  }
}

export const tipoLocalRoute: Routes = [
  {
    path: '',
    component: TipoLocalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mmgestorApp.tipoLocal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoLocalDetailComponent,
    resolve: {
      tipoLocal: TipoLocalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.tipoLocal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoLocalUpdateComponent,
    resolve: {
      tipoLocal: TipoLocalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.tipoLocal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoLocalUpdateComponent,
    resolve: {
      tipoLocal: TipoLocalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.tipoLocal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
