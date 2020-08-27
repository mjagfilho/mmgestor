import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from './animal.service';

@Component({
  templateUrl: './animal-delete-dialog.component.html',
})
export class AnimalDeleteDialogComponent {
  animal?: IAnimal;

  constructor(protected animalService: AnimalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.animalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('animalListModification');
      this.activeModal.close();
    });
  }
}
