import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDadosAssociacao } from 'app/shared/model/dados-associacao.model';
import { DadosAssociacaoService } from './dados-associacao.service';

@Component({
  templateUrl: './dados-associacao-delete-dialog.component.html'
})
export class DadosAssociacaoDeleteDialogComponent {
  dadosAssociacao?: IDadosAssociacao;

  constructor(
    protected dadosAssociacaoService: DadosAssociacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dadosAssociacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dadosAssociacaoListModification');
      this.activeModal.close();
    });
  }
}
