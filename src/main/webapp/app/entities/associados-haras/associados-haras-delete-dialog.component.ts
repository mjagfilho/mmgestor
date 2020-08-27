import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssociadosHaras } from 'app/shared/model/associados-haras.model';
import { AssociadosHarasService } from './associados-haras.service';

@Component({
  templateUrl: './associados-haras-delete-dialog.component.html',
})
export class AssociadosHarasDeleteDialogComponent {
  associadosHaras?: IAssociadosHaras;

  constructor(
    protected associadosHarasService: AssociadosHarasService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.associadosHarasService.delete(id).subscribe(() => {
      this.eventManager.broadcast('associadosHarasListModification');
      this.activeModal.close();
    });
  }
}
