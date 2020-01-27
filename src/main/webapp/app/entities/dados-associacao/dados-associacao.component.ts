import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDadosAssociacao } from 'app/shared/model/dados-associacao.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DadosAssociacaoService } from './dados-associacao.service';
import { DadosAssociacaoDeleteDialogComponent } from './dados-associacao-delete-dialog.component';

@Component({
  selector: 'jhi-dados-associacao',
  templateUrl: './dados-associacao.component.html'
})
export class DadosAssociacaoComponent implements OnInit, OnDestroy {
  dadosAssociacaos?: IDadosAssociacao[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected dadosAssociacaoService: DadosAssociacaoService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page ? page : this.page;
    this.dadosAssociacaoService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IDadosAssociacao[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInDadosAssociacaos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDadosAssociacao): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDadosAssociacaos(): void {
    this.eventSubscriber = this.eventManager.subscribe('dadosAssociacaoListModification', () => this.loadPage());
  }

  delete(dadosAssociacao: IDadosAssociacao): void {
    const modalRef = this.modalService.open(DadosAssociacaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dadosAssociacao = dadosAssociacao;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IDadosAssociacao[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/dados-associacao'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.dadosAssociacaos = data ? data : [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
