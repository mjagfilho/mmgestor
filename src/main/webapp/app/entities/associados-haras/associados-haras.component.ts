import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAssociadosHaras } from 'app/shared/model/associados-haras.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AssociadosHarasService } from './associados-haras.service';
import { AssociadosHarasDeleteDialogComponent } from './associados-haras-delete-dialog.component';

@Component({
  selector: 'jhi-associados-haras',
  templateUrl: './associados-haras.component.html'
})
export class AssociadosHarasComponent implements OnInit, OnDestroy {
  associadosHaras?: IAssociadosHaras[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected associadosHarasService: AssociadosHarasService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.associadosHarasService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IAssociadosHaras[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInAssociadosHaras();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAssociadosHaras): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAssociadosHaras(): void {
    this.eventSubscriber = this.eventManager.subscribe('associadosHarasListModification', () => this.loadPage());
  }

  delete(associadosHaras: IAssociadosHaras): void {
    const modalRef = this.modalService.open(AssociadosHarasDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.associadosHaras = associadosHaras;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IAssociadosHaras[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/associados-haras'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.associadosHaras = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
