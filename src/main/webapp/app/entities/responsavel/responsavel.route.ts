import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResponsavel, Responsavel } from 'app/shared/model/responsavel.model';
import { ResponsavelService } from './responsavel.service';
import { ResponsavelComponent } from './responsavel.component';
import { ResponsavelDetailComponent } from './responsavel-detail.component';
import { ResponsavelUpdateComponent } from './responsavel-update.component';

@Injectable({ providedIn: 'root' })
export class ResponsavelResolve implements Resolve<IResponsavel> {
  constructor(private service: ResponsavelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResponsavel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((responsavel: HttpResponse<Responsavel>) => {
          if (responsavel.body) {
            return of(responsavel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Responsavel());
  }
}

export const responsavelRoute: Routes = [
  {
    path: '',
    component: ResponsavelComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mmgestorApp.responsavel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResponsavelDetailComponent,
    resolve: {
      responsavel: ResponsavelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.responsavel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResponsavelUpdateComponent,
    resolve: {
      responsavel: ResponsavelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.responsavel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResponsavelUpdateComponent,
    resolve: {
      responsavel: ResponsavelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.responsavel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
