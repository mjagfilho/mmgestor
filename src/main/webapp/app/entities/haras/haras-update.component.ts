import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHaras, Haras } from 'app/shared/model/haras.model';
import { HarasService } from './haras.service';
import { IAssociado } from 'app/shared/model/associado.model';
import { AssociadoService } from 'app/entities/associado/associado.service';

@Component({
  selector: 'jhi-haras-update',
  templateUrl: './haras-update.component.html',
})
export class HarasUpdateComponent implements OnInit {
  isSaving = false;
  associados: IAssociado[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    localidade: [null, [Validators.required]],
    uf: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(2), Validators.pattern('[A-Z]{2}')]],
    responsavel: [null, Validators.required],
  });

  constructor(
    protected harasService: HarasService,
    protected associadoService: AssociadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ haras }) => {
      this.updateForm(haras);

      this.associadoService.query().subscribe((res: HttpResponse<IAssociado[]>) => (this.associados = res.body || []));
    });
  }

  updateForm(haras: IHaras): void {
    this.editForm.patchValue({
      id: haras.id,
      nome: haras.nome,
      localidade: haras.localidade,
      uf: haras.uf,
      responsavel: haras.responsavel,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const haras = this.createFromForm();
    if (haras.id !== undefined) {
      this.subscribeToSaveResponse(this.harasService.update(haras));
    } else {
      this.subscribeToSaveResponse(this.harasService.create(haras));
    }
  }

  private createFromForm(): IHaras {
    return {
      ...new Haras(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      localidade: this.editForm.get(['localidade'])!.value,
      uf: this.editForm.get(['uf'])!.value,
      responsavel: this.editForm.get(['responsavel'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHaras>>): void {
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

  trackById(index: number, item: IAssociado): any {
    return item.id;
  }
}
