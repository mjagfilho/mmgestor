import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocal } from 'app/shared/model/local.model';
import { LocalService } from './local.service';

@Component({
  templateUrl: './local-delete-dialog.component.html'
})
export class LocalDeleteDialogComponent {
  local?: ILocal;

  constructor(protected localService: LocalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.localService.delete(id).subscribe(() => {
      this.eventManager.broadcast('localListModification');
      this.activeModal.close();
    });
  }
}
