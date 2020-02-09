import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssociado } from 'app/shared/model/associado.model';
import { AssociadoService } from './associado.service';

@Component({
  templateUrl: './associado-delete-dialog.component.html'
})
export class AssociadoDeleteDialogComponent {
  associado?: IAssociado;

  constructor(protected associadoService: AssociadoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.associadoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('associadoListModification');
      this.activeModal.close();
    });
  }
}
