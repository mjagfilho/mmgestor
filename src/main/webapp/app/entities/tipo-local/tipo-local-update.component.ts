import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoLocal, TipoLocal } from 'app/shared/model/tipo-local.model';
import { TipoLocalService } from './tipo-local.service';

@Component({
  selector: 'jhi-tipo-local-update',
  templateUrl: './tipo-local-update.component.html'
})
export class TipoLocalUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    descricao: []
  });

  constructor(protected tipoLocalService: TipoLocalService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoLocal }) => {
      this.updateForm(tipoLocal);
    });
  }

  updateForm(tipoLocal: ITipoLocal): void {
    this.editForm.patchValue({
      id: tipoLocal.id,
      nome: tipoLocal.nome,
      descricao: tipoLocal.descricao
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoLocal = this.createFromForm();
    if (tipoLocal.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoLocalService.update(tipoLocal));
    } else {
      this.subscribeToSaveResponse(this.tipoLocalService.create(tipoLocal));
    }
  }

  private createFromForm(): ITipoLocal {
    return {
      ...new TipoLocal(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoLocal>>): void {
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
