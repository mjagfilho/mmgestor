import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoAssociado, TipoAssociado } from 'app/shared/model/tipo-associado.model';
import { TipoAssociadoService } from './tipo-associado.service';

@Component({
  selector: 'jhi-tipo-associado-update',
  templateUrl: './tipo-associado-update.component.html'
})
export class TipoAssociadoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    descricao: [],
    ehAdministrador: [null, [Validators.required]],
    ehFinanceiro: [null, [Validators.required]],
    ehOperacional: [null, [Validators.required]],
    ehVeterinario: [null, [Validators.required]]
  });

  constructor(protected tipoAssociadoService: TipoAssociadoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoAssociado }) => {
      this.updateForm(tipoAssociado);
    });
  }

  updateForm(tipoAssociado: ITipoAssociado): void {
    this.editForm.patchValue({
      id: tipoAssociado.id,
      nome: tipoAssociado.nome,
      descricao: tipoAssociado.descricao,
      ehAdministrador: tipoAssociado.ehAdministrador,
      ehFinanceiro: tipoAssociado.ehFinanceiro,
      ehOperacional: tipoAssociado.ehOperacional,
      ehVeterinario: tipoAssociado.ehVeterinario
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoAssociado = this.createFromForm();
    if (tipoAssociado.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoAssociadoService.update(tipoAssociado));
    } else {
      this.subscribeToSaveResponse(this.tipoAssociadoService.create(tipoAssociado));
    }
  }

  private createFromForm(): ITipoAssociado {
    return {
      ...new TipoAssociado(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      ehAdministrador: this.editForm.get(['ehAdministrador'])!.value,
      ehFinanceiro: this.editForm.get(['ehFinanceiro'])!.value,
      ehOperacional: this.editForm.get(['ehOperacional'])!.value,
      ehVeterinario: this.editForm.get(['ehVeterinario'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoAssociado>>): void {
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
