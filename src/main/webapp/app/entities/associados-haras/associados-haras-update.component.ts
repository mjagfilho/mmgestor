import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IAssociadosHaras, AssociadosHaras } from 'app/shared/model/associados-haras.model';
import { AssociadosHarasService } from './associados-haras.service';
import { IAssociado } from 'app/shared/model/associado.model';
import { AssociadoService } from 'app/entities/associado/associado.service';
import { IHaras } from 'app/shared/model/haras.model';
import { HarasService } from 'app/entities/haras/haras.service';

type SelectableEntity = IAssociado | IHaras;

@Component({
  selector: 'jhi-associados-haras-update',
  templateUrl: './associados-haras-update.component.html'
})
export class AssociadosHarasUpdateComponent implements OnInit {
  isSaving = false;

  associados: IAssociado[] = [];

  haras: IHaras[] = [];
  dataAssociacaoDp: any;

  editForm = this.fb.group({
    id: [],
    dataAssociacao: [null, [Validators.required]],
    ehAtivo: [null, [Validators.required]],
    associado: [null, Validators.required],
    haras: [null, Validators.required]
  });

  constructor(
    protected associadosHarasService: AssociadosHarasService,
    protected associadoService: AssociadoService,
    protected harasService: HarasService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ associadosHaras }) => {
      this.updateForm(associadosHaras);

      this.associadoService
        .query()
        .pipe(
          map((res: HttpResponse<IAssociado[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAssociado[]) => (this.associados = resBody));

      this.harasService
        .query()
        .pipe(
          map((res: HttpResponse<IHaras[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IHaras[]) => (this.haras = resBody));
    });
  }

  updateForm(associadosHaras: IAssociadosHaras): void {
    this.editForm.patchValue({
      id: associadosHaras.id,
      dataAssociacao: associadosHaras.dataAssociacao,
      ehAtivo: associadosHaras.ehAtivo,
      associado: associadosHaras.associado,
      haras: associadosHaras.haras
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const associadosHaras = this.createFromForm();
    if (associadosHaras.id !== undefined) {
      this.subscribeToSaveResponse(this.associadosHarasService.update(associadosHaras));
    } else {
      this.subscribeToSaveResponse(this.associadosHarasService.create(associadosHaras));
    }
  }

  private createFromForm(): IAssociadosHaras {
    return {
      ...new AssociadosHaras(),
      id: this.editForm.get(['id'])!.value,
      dataAssociacao: this.editForm.get(['dataAssociacao'])!.value,
      ehAtivo: this.editForm.get(['ehAtivo'])!.value,
      associado: this.editForm.get(['associado'])!.value,
      haras: this.editForm.get(['haras'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssociadosHaras>>): void {
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
