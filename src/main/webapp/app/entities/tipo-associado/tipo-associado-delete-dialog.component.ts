import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoAssociado } from 'app/shared/model/tipo-associado.model';
import { TipoAssociadoService } from './tipo-associado.service';

@Component({
  templateUrl: './tipo-associado-delete-dialog.component.html',
})
export class TipoAssociadoDeleteDialogComponent {
  tipoAssociado?: ITipoAssociado;

  constructor(
    protected tipoAssociadoService: TipoAssociadoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoAssociadoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoAssociadoListModification');
      this.activeModal.close();
    });
  }
}
