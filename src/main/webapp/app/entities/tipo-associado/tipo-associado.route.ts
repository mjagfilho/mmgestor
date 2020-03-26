import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoAssociado, TipoAssociado } from 'app/shared/model/tipo-associado.model';
import { TipoAssociadoService } from './tipo-associado.service';
import { TipoAssociadoComponent } from './tipo-associado.component';
import { TipoAssociadoDetailComponent } from './tipo-associado-detail.component';
import { TipoAssociadoUpdateComponent } from './tipo-associado-update.component';

@Injectable({ providedIn: 'root' })
export class TipoAssociadoResolve implements Resolve<ITipoAssociado> {
  constructor(private service: TipoAssociadoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoAssociado> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoAssociado: HttpResponse<TipoAssociado>) => {
          if (tipoAssociado.body) {
            return of(tipoAssociado.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoAssociado());
  }
}

export const tipoAssociadoRoute: Routes = [
  {
    path: '',
    component: TipoAssociadoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mmgestorApp.tipoAssociado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoAssociadoDetailComponent,
    resolve: {
      tipoAssociado: TipoAssociadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.tipoAssociado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoAssociadoUpdateComponent,
    resolve: {
      tipoAssociado: TipoAssociadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.tipoAssociado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoAssociadoUpdateComponent,
    resolve: {
      tipoAssociado: TipoAssociadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.tipoAssociado.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
