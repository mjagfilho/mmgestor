import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAssociado, Associado } from 'app/shared/model/associado.model';
import { AssociadoService } from './associado.service';
import { AssociadoComponent } from './associado.component';
import { AssociadoDetailComponent } from './associado-detail.component';
import { AssociadoUpdateComponent } from './associado-update.component';

@Injectable({ providedIn: 'root' })
export class AssociadoResolve implements Resolve<IAssociado> {
  constructor(private service: AssociadoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssociado> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((associado: HttpResponse<Associado>) => {
          if (associado.body) {
            return of(associado.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Associado());
  }
}

export const associadoRoute: Routes = [
  {
    path: '',
    component: AssociadoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mmgestorApp.associado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AssociadoDetailComponent,
    resolve: {
      associado: AssociadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.associado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AssociadoUpdateComponent,
    resolve: {
      associado: AssociadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.associado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AssociadoUpdateComponent,
    resolve: {
      associado: AssociadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.associado.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
