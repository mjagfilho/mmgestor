import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResponsavel } from 'app/shared/model/responsavel.model';
import { ResponsavelService } from './responsavel.service';

@Component({
  templateUrl: './responsavel-delete-dialog.component.html'
})
export class ResponsavelDeleteDialogComponent {
  responsavel?: IResponsavel;

  constructor(
    protected responsavelService: ResponsavelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.responsavelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('responsavelListModification');
      this.activeModal.close();
    });
  }
}
