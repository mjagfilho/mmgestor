import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoAssociado } from 'app/shared/model/tipo-associado.model';

@Component({
  selector: 'jhi-tipo-associado-detail',
  templateUrl: './tipo-associado-detail.component.html'
})
export class TipoAssociadoDetailComponent implements OnInit {
  tipoAssociado: ITipoAssociado | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoAssociado }) => (this.tipoAssociado = tipoAssociado));
  }

  previousState(): void {
    window.history.back();
  }
}
