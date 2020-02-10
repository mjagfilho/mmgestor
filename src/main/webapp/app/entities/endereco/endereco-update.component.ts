import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEndereco, Endereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from './endereco.service';
import { CEP_MASK } from 'app/shared/constants/input.constants';
import { NgxViacepService, Endereco as EnderecoViaCep, ErroCep, ErrorValues } from '@brunoc/ngx-viacep';

@Component({
  selector: 'jhi-endereco-update',
  templateUrl: './endereco-update.component.html'
})
export class EnderecoUpdateComponent implements OnInit {
  isSaving = false;
  cepMask = CEP_MASK;

  editForm = this.fb.group({
    id: [],
    cep: [
      null,
      [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('[0-9]{2}.[0-9]{3}-[0-9]{3}')]
    ],
    logradouro: [null, [Validators.required]],
    complemento: [],
    bairro: [null, [Validators.required]],
    localidade: [null, [Validators.required]],
    uf: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(2), Validators.pattern('[A-Z]{2}')]]
  });

  constructor(
    protected enderecoService: EnderecoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private viacep: NgxViacepService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endereco }) => {
      this.updateForm(endereco);
    });
  }

  updateForm(endereco: IEndereco): void {
    this.editForm.patchValue({
      id: endereco.id,
      cep: endereco.cep,
      logradouro: endereco.logradouro,
      complemento: endereco.complemento,
      bairro: endereco.bairro,
      localidade: endereco.localidade,
      uf: endereco.uf
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const endereco = this.createFromForm();
    if (endereco.id !== undefined) {
      this.subscribeToSaveResponse(this.enderecoService.update(endereco));
    } else {
      this.subscribeToSaveResponse(this.enderecoService.create(endereco));
    }
  }

  private createFromForm(): IEndereco {
    return {
      ...new Endereco(),
      id: this.editForm.get(['id'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      logradouro: this.editForm.get(['logradouro'])!.value,
      complemento: this.editForm.get(['complemento'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      localidade: this.editForm.get(['localidade'])!.value,
      uf: this.editForm.get(['uf'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndereco>>): void {
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

  consultarCEP(): void {
    this.viacep
      .buscarPorCep('52041360')
      .then((endereco: EnderecoViaCep) => {
        // Endereço retornado :)
        console.log(endereco);
      })
      .catch((error: ErroCep) => {
        // Alguma coisa deu errado :/
        console.log(error.message);
        switch (error.getCode()) {
          case ErrorValues.CEP_NAO_ENCONTRADO:
            console.log('CEP não encontrado!');
            break;
          case ErrorValues.ERRO_SERVIDOR:
            console.log('O serviço de consulta está enfrentando instabilidade');
            break;
          // Quaisquer outros erros contidos em ErrorValues podem ser tratados assim
        }
      });
  }
}
