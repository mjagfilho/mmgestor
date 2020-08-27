import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoLocal } from 'app/shared/model/tipo-local.model';

@Component({
  selector: 'jhi-tipo-local-detail',
  templateUrl: './tipo-local-detail.component.html',
})
export class TipoLocalDetailComponent implements OnInit {
  tipoLocal: ITipoLocal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoLocal }) => (this.tipoLocal = tipoLocal));
  }

  previousState(): void {
    window.history.back();
  }
}
