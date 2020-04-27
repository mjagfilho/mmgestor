import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClienteFornecedor } from 'app/shared/model/cliente-fornecedor.model';

@Component({
  selector: 'jhi-cliente-fornecedor-detail',
  templateUrl: './cliente-fornecedor-detail.component.html'
})
export class ClienteFornecedorDetailComponent implements OnInit {
  clienteFornecedor: IClienteFornecedor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clienteFornecedor }) => (this.clienteFornecedor = clienteFornecedor));
  }

  previousState(): void {
    window.history.back();
  }
}
