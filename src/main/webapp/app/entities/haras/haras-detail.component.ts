import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHaras } from 'app/shared/model/haras.model';

@Component({
  selector: 'jhi-haras-detail',
  templateUrl: './haras-detail.component.html',
})
export class HarasDetailComponent implements OnInit {
  haras: IHaras | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ haras }) => (this.haras = haras));
  }

  previousState(): void {
    window.history.back();
  }
}
