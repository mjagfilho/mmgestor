import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHaras, Haras } from 'app/shared/model/haras.model';
import { HarasService } from './haras.service';
import { HarasComponent } from './haras.component';
import { HarasDetailComponent } from './haras-detail.component';
import { HarasUpdateComponent } from './haras-update.component';

@Injectable({ providedIn: 'root' })
export class HarasResolve implements Resolve<IHaras> {
  constructor(private service: HarasService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHaras> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((haras: HttpResponse<Haras>) => {
          if (haras.body) {
            return of(haras.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Haras());
  }
}

export const harasRoute: Routes = [
  {
    path: '',
    component: HarasComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mmgestorApp.haras.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HarasDetailComponent,
    resolve: {
      haras: HarasResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mmgestorApp.haras.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HarasUpdateComponent,
    resolve: {
      haras: HarasResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mmgestorApp.haras.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HarasUpdateComponent,
    resolve: {
      haras: HarasResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mmgestorApp.haras.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
