import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClienteFornecedor } from 'app/shared/model/cliente-fornecedor.model';
import { ClienteFornecedorService } from './cliente-fornecedor.service';

@Component({
  templateUrl: './cliente-fornecedor-delete-dialog.component.html'
})
export class ClienteFornecedorDeleteDialogComponent {
  clienteFornecedor?: IClienteFornecedor;

  constructor(
    protected clienteFornecedorService: ClienteFornecedorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.clienteFornecedorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('clienteFornecedorListModification');
      this.activeModal.close();
    });
  }
}
