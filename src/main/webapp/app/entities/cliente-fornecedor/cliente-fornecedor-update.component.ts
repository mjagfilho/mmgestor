import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IClienteFornecedor, ClienteFornecedor } from 'app/shared/model/cliente-fornecedor.model';
import { ClienteFornecedorService } from './cliente-fornecedor.service';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco/endereco.service';

@Component({
  selector: 'jhi-cliente-fornecedor-update',
  templateUrl: './cliente-fornecedor-update.component.html'
})
export class ClienteFornecedorUpdateComponent implements OnInit {
  isSaving = false;

  enderecos: IEndereco[] = [];

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
    endereco: [null, Validators.required]
  });

  constructor(
    protected clienteFornecedorService: ClienteFornecedorService,
    protected enderecoService: EnderecoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clienteFornecedor }) => {
      this.updateForm(clienteFornecedor);

      this.enderecoService
        .query({ filter: 'clientefornecedor-is-null' })
        .pipe(
          map((res: HttpResponse<IEndereco[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEndereco[]) => {
          if (!clienteFornecedor.endereco || !clienteFornecedor.endereco.id) {
            this.enderecos = resBody;
          } else {
            this.enderecoService
              .find(clienteFornecedor.endereco.id)
              .pipe(
                map((subRes: HttpResponse<IEndereco>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEndereco[]) => {
                this.enderecos = concatRes;
              });
          }
        });
    });
  }

  updateForm(clienteFornecedor: IClienteFornecedor): void {
    this.editForm.patchValue({
      id: clienteFornecedor.id,
      nome: clienteFornecedor.nome,
      dtNascimento: clienteFornecedor.dtNascimento != null ? clienteFornecedor.dtNascimento.format(DATE_TIME_FORMAT) : null,
      cpf: clienteFornecedor.cpf,
      nomeHaras: clienteFornecedor.nomeHaras,
      localidadeHaras: clienteFornecedor.localidadeHaras,
      ufHaras: clienteFornecedor.ufHaras,
      endereco: clienteFornecedor.endereco
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
      dtNascimento:
        this.editForm.get(['dtNascimento'])!.value != null
          ? moment(this.editForm.get(['dtNascimento'])!.value, DATE_TIME_FORMAT)
          : undefined,
      cpf: this.editForm.get(['cpf'])!.value,
      nomeHaras: this.editForm.get(['nomeHaras'])!.value,
      localidadeHaras: this.editForm.get(['localidadeHaras'])!.value,
      ufHaras: this.editForm.get(['ufHaras'])!.value,
      endereco: this.editForm.get(['endereco'])!.value
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

  trackById(index: number, item: IEndereco): any {
    return item.id;
  }
}
