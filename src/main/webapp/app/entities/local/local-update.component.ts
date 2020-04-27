import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILocal, Local } from 'app/shared/model/local.model';
import { LocalService } from './local.service';
import { ITipoLocal } from 'app/shared/model/tipo-local.model';
import { TipoLocalService } from 'app/entities/tipo-local/tipo-local.service';

type SelectableEntity = ITipoLocal | ILocal;

@Component({
  selector: 'jhi-local-update',
  templateUrl: './local-update.component.html'
})
export class LocalUpdateComponent implements OnInit {
  isSaving = false;
  tipos: ITipoLocal[] = [];
  locals: ILocal[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    area: [null, [Validators.required]],
    ehContigua: [null, [Validators.required]],
    cep: [null, [Validators.required, Validators.minLength(9), Validators.maxLength(9), Validators.pattern('[0-9]{5}-[0-9]{3}')]],
    logradouro: [null, [Validators.required]],
    numero: [null, [Validators.required]],
    complemento: [],
    bairro: [null, [Validators.required]],
    localidade: [null, [Validators.required]],
    uf: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(2), Validators.pattern('[A-Z]{2}')]],
    tipo: [null, Validators.required],
    pai: []
  });

  constructor(
    protected localService: LocalService,
    protected tipoLocalService: TipoLocalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ local }) => {
      this.updateForm(local);

      this.tipoLocalService
        .query({ filter: 'local-is-null' })
        .pipe(
          map((res: HttpResponse<ITipoLocal[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITipoLocal[]) => {
          if (!local.tipo || !local.tipo.id) {
            this.tipos = resBody;
          } else {
            this.tipoLocalService
              .find(local.tipo.id)
              .pipe(
                map((subRes: HttpResponse<ITipoLocal>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITipoLocal[]) => (this.tipos = concatRes));
          }
        });

      this.localService.query().subscribe((res: HttpResponse<ILocal[]>) => (this.locals = res.body || []));
    });
  }

  updateForm(local: ILocal): void {
    this.editForm.patchValue({
      id: local.id,
      nome: local.nome,
      area: local.area,
      ehContigua: local.ehContigua,
      cep: local.cep,
      logradouro: local.logradouro,
      numero: local.numero,
      complemento: local.complemento,
      bairro: local.bairro,
      localidade: local.localidade,
      uf: local.uf,
      tipo: local.tipo,
      pai: local.pai
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const local = this.createFromForm();
    if (local.id !== undefined) {
      this.subscribeToSaveResponse(this.localService.update(local));
    } else {
      this.subscribeToSaveResponse(this.localService.create(local));
    }
  }

  private createFromForm(): ILocal {
    return {
      ...new Local(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      area: this.editForm.get(['area'])!.value,
      ehContigua: this.editForm.get(['ehContigua'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      logradouro: this.editForm.get(['logradouro'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      complemento: this.editForm.get(['complemento'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      localidade: this.editForm.get(['localidade'])!.value,
      uf: this.editForm.get(['uf'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      pai: this.editForm.get(['pai'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocal>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
