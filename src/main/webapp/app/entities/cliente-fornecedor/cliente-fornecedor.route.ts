import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IClienteFornecedor, ClienteFornecedor } from 'app/shared/model/cliente-fornecedor.model';
import { ClienteFornecedorService } from './cliente-fornecedor.service';
import { ClienteFornecedorComponent } from './cliente-fornecedor.component';
import { ClienteFornecedorDetailComponent } from './cliente-fornecedor-detail.component';
import { ClienteFornecedorUpdateComponent } from './cliente-fornecedor-update.component';

@Injectable({ providedIn: 'root' })
export class ClienteFornecedorResolve implements Resolve<IClienteFornecedor> {
  constructor(private service: ClienteFornecedorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClienteFornecedor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((clienteFornecedor: HttpResponse<ClienteFornecedor>) => {
          if (clienteFornecedor.body) {
            return of(clienteFornecedor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClienteFornecedor());
  }
}

export const clienteFornecedorRoute: Routes = [
  {
    path: '',
    component: ClienteFornecedorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mmgestorApp.clienteFornecedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClienteFornecedorDetailComponent,
    resolve: {
      clienteFornecedor: ClienteFornecedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.clienteFornecedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClienteFornecedorUpdateComponent,
    resolve: {
      clienteFornecedor: ClienteFornecedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.clienteFornecedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClienteFornecedorUpdateComponent,
    resolve: {
      clienteFornecedor: ClienteFornecedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.clienteFornecedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
