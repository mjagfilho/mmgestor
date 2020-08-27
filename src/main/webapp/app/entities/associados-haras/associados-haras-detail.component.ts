import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssociadosHaras } from 'app/shared/model/associados-haras.model';

@Component({
  selector: 'jhi-associados-haras-detail',
  templateUrl: './associados-haras-detail.component.html',
})
export class AssociadosHarasDetailComponent implements OnInit {
  associadosHaras: IAssociadosHaras | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ associadosHaras }) => (this.associadosHaras = associadosHaras));
  }

  previousState(): void {
    window.history.back();
  }
}
