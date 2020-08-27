import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAssociadosHaras, AssociadosHaras } from 'app/shared/model/associados-haras.model';
import { AssociadosHarasService } from './associados-haras.service';
import { AssociadosHarasComponent } from './associados-haras.component';
import { AssociadosHarasDetailComponent } from './associados-haras-detail.component';
import { AssociadosHarasUpdateComponent } from './associados-haras-update.component';

@Injectable({ providedIn: 'root' })
export class AssociadosHarasResolve implements Resolve<IAssociadosHaras> {
  constructor(private service: AssociadosHarasService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssociadosHaras> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((associadosHaras: HttpResponse<AssociadosHaras>) => {
          if (associadosHaras.body) {
            return of(associadosHaras.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AssociadosHaras());
  }
}

export const associadosHarasRoute: Routes = [
  {
    path: '',
    component: AssociadosHarasComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mmgestorApp.associadosHaras.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AssociadosHarasDetailComponent,
    resolve: {
      associadosHaras: AssociadosHarasResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mmgestorApp.associadosHaras.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AssociadosHarasUpdateComponent,
    resolve: {
      associadosHaras: AssociadosHarasResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mmgestorApp.associadosHaras.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AssociadosHarasUpdateComponent,
    resolve: {
      associadosHaras: AssociadosHarasResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mmgestorApp.associadosHaras.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
