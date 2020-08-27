import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IClienteFornecedor, ClienteFornecedor } from 'app/shared/model/cliente-fornecedor.model';
import { ClienteFornecedorService } from './cliente-fornecedor.service';

@Component({
  selector: 'jhi-cliente-fornecedor-update',
  templateUrl: './cliente-fornecedor-update.component.html'
})
export class ClienteFornecedorUpdateComponent implements OnInit {
  isSaving = false;
  dtNascimentoDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    dtNascimento: [],
    cpf: [
      null,
      [Validators.required, Validators.minLength(14), Validators.maxLength(14), Validators.pattern('[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}')]
    ],
    nomeHaras: [null, [Validators.required]],
    localidadeHaras: [null, [Validators.required]],
    ufHaras: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(2), Validators.pattern('[A-Z]{2}')]],
    cep: [null, [Validators.required, Validators.minLength(9), Validators.maxLength(9), Validators.pattern('[0-9]{5}-[0-9]{3}')]],
    logradouro: [null, [Validators.required]],
    numero: [null, [Validators.required]],
    complemento: [],
    bairro: [null, [Validators.required]],
    localidade: [null, [Validators.required]],
    uf: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(2), Validators.pattern('[A-Z]{2}')]]
  });

  constructor(
    protected clienteFornecedorService: ClienteFornecedorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clienteFornecedor }) => {
      this.updateForm(clienteFornecedor);
    });
  }

  updateForm(clienteFornecedor: IClienteFornecedor): void {
    this.editForm.patchValue({
      id: clienteFornecedor.id,
      nome: clienteFornecedor.nome,
      dtNascimento: clienteFornecedor.dtNascimento,
      cpf: clienteFornecedor.cpf,
      nomeHaras: clienteFornecedor.nomeHaras,
      localidadeHaras: clienteFornecedor.localidadeHaras,
      ufHaras: clienteFornecedor.ufHaras,
      cep: clienteFornecedor.cep,
      logradouro: clienteFornecedor.logradouro,
      numero: clienteFornecedor.numero,
      complemento: clienteFornecedor.complemento,
      bairro: clienteFornecedor.bairro,
      localidade: clienteFornecedor.localidade,
      uf: clienteFornecedor.uf
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const clienteFornecedor = this.createFromForm();
    if (clienteFornecedor.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteFornecedorService.update(clienteFornecedor));
    } else {
      this.subscribeToSaveResponse(this.clienteFornecedorService.create(clienteFornecedor));
    }
  }

  private createFromForm(): IClienteFornecedor {
    return {
      ...new ClienteFornecedor(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      dtNascimento: this.editForm.get(['dtNascimento'])!.value,
      cpf: this.editForm.get(['cpf'])!.value,
      nomeHaras: this.editForm.get(['nomeHaras'])!.value,
      localidadeHaras: this.editForm.get(['localidadeHaras'])!.value,
      ufHaras: this.editForm.get(['ufHaras'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      logradouro: this.editForm.get(['logradouro'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      complemento: this.editForm.get(['complemento'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      localidade: this.editForm.get(['localidade'])!.value,
      uf: this.editForm.get(['uf'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClienteFornecedor>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
