import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDadosAssociacao, DadosAssociacao } from 'app/shared/model/dados-associacao.model';
import { DadosAssociacaoService } from './dados-associacao.service';

@Component({
  selector: 'jhi-dados-associacao-update',
  templateUrl: './dados-associacao-update.component.html',
})
export class DadosAssociacaoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    criador: [],
    proprietario: [],
    livro: [],
    registro: [null, []],
    exameDNA: [],
    chip: [null, []],
    ehBloqueado: [],
  });

  constructor(
    protected dadosAssociacaoService: DadosAssociacaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dadosAssociacao }) => {
      this.updateForm(dadosAssociacao);
    });
  }

  updateForm(dadosAssociacao: IDadosAssociacao): void {
    this.editForm.patchValue({
      id: dadosAssociacao.id,
      criador: dadosAssociacao.criador,
      proprietario: dadosAssociacao.proprietario,
      livro: dadosAssociacao.livro,
      registro: dadosAssociacao.registro,
      exameDNA: dadosAssociacao.exameDNA,
      chip: dadosAssociacao.chip,
      ehBloqueado: dadosAssociacao.ehBloqueado,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dadosAssociacao = this.createFromForm();
    if (dadosAssociacao.id !== undefined) {
      this.subscribeToSaveResponse(this.dadosAssociacaoService.update(dadosAssociacao));
    } else {
      this.subscribeToSaveResponse(this.dadosAssociacaoService.create(dadosAssociacao));
    }
  }

  private createFromForm(): IDadosAssociacao {
    return {
      ...new DadosAssociacao(),
      id: this.editForm.get(['id'])!.value,
      criador: this.editForm.get(['criador'])!.value,
      proprietario: this.editForm.get(['proprietario'])!.value,
      livro: this.editForm.get(['livro'])!.value,
      registro: this.editForm.get(['registro'])!.value,
      exameDNA: this.editForm.get(['exameDNA'])!.value,
      chip: this.editForm.get(['chip'])!.value,
      ehBloqueado: this.editForm.get(['ehBloqueado'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDadosAssociacao>>): void {
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
