import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAnimal, Animal } from 'app/shared/model/animal.model';
import { AnimalService } from './animal.service';
import { AnimalComponent } from './animal.component';
import { AnimalDetailComponent } from './animal-detail.component';
import { AnimalUpdateComponent } from './animal-update.component';

@Injectable({ providedIn: 'root' })
export class AnimalResolve implements Resolve<IAnimal> {
  constructor(private service: AnimalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnimal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((animal: HttpResponse<Animal>) => {
          if (animal.body) {
            return of(animal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Animal());
  }
}

export const animalRoute: Routes = [
  {
    path: '',
    component: AnimalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'mmgestorApp.animal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnimalDetailComponent,
    resolve: {
      animal: AnimalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.animal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnimalUpdateComponent,
    resolve: {
      animal: AnimalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.animal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnimalUpdateComponent,
    resolve: {
      animal: AnimalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mmgestorApp.animal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
