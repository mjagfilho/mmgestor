import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnimal } from 'app/shared/model/animal.model';

@Component({
  selector: 'jhi-animal-detail',
  templateUrl: './animal-detail.component.html',
})
export class AnimalDetailComponent implements OnInit {
  animal: IAnimal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ animal }) => (this.animal = animal));
  }

  previousState(): void {
    window.history.back();
  }
}
