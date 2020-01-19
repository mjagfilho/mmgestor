import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDadosAssociacao, DadosAssociacao } from 'app/shared/model/dados-associacao.model';
import { DadosAssociacaoService } from './dados-associacao.service';
import { DadosAssociacaoComponent } from './dados-associacao.component';
import { DadosAssociacaoDetailComponent } from './dados-associacao-detail.component';
import { DadosAssociacaoUpdateComponent } from './dados-associacao-update.component';

@Injectable({ providedIn: 'root' })
export class DadosAssociacaoResolve implements Resolve<IDadosAssociacao> {
  constructor(private service: DadosAssociacaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDadosAssociacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dadosAssociacao: HttpResponse<DadosAssociacao>) => {
          if (dadosAssociacao.body) {
            return of(dadosAssociacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DadosAssociacao());
  }
}

export const dadosAssociacaoRoute: Routes = [
  {
    path: '',
    component: DadosAssociacaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mmGestorApp.dadosAssociacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DadosAssociacaoDetailComponent,
    resolve: {
      dadosAssociacao: DadosAssociacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmGestorApp.dadosAssociacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DadosAssociacaoUpdateComponent,
    resolve: {
      dadosAssociacao: DadosAssociacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmGestorApp.dadosAssociacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DadosAssociacaoUpdateComponent,
    resolve: {
      dadosAssociacao: DadosAssociacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmGestorApp.dadosAssociacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
