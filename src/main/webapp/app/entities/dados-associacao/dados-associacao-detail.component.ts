import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDadosAssociacao } from 'app/shared/model/dados-associacao.model';

@Component({
  selector: 'jhi-dados-associacao-detail',
  templateUrl: './dados-associacao-detail.component.html'
})
export class DadosAssociacaoDetailComponent implements OnInit {
  dadosAssociacao: IDadosAssociacao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dadosAssociacao }) => {
      this.dadosAssociacao = dadosAssociacao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
