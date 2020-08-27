import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHaras } from 'app/shared/model/haras.model';
import { HarasService } from './haras.service';

@Component({
  templateUrl: './haras-delete-dialog.component.html',
})
export class HarasDeleteDialogComponent {
  haras?: IHaras;

  constructor(protected harasService: HarasService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.harasService.delete(id).subscribe(() => {
      this.eventManager.broadcast('harasListModification');
      this.activeModal.close();
    });
  }
}
