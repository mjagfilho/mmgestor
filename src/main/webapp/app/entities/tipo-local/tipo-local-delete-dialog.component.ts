import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoLocal } from 'app/shared/model/tipo-local.model';
import { TipoLocalService } from './tipo-local.service';

@Component({
  templateUrl: './tipo-local-delete-dialog.component.html'
})
export class TipoLocalDeleteDialogComponent {
  tipoLocal?: ITipoLocal;

  constructor(protected tipoLocalService: TipoLocalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoLocalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoLocalListModification');
      this.activeModal.close();
    });
  }
}
