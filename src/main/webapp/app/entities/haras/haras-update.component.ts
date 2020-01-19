import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IHaras, Haras } from 'app/shared/model/haras.model';
import { HarasService } from './haras.service';
import { IResponsavel } from 'app/shared/model/responsavel.model';
import { ResponsavelService } from 'app/entities/responsavel/responsavel.service';

@Component({
  selector: 'jhi-haras-update',
  templateUrl: './haras-update.component.html'
})
export class HarasUpdateComponent implements OnInit {
  isSaving = false;

  responsavels: IResponsavel[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    localidade: [null, [Validators.required]],
    uf: [null, [Validators.required]],
    responsavel: [null, Validators.required]
  });

  constructor(
    protected harasService: HarasService,
    protected responsavelService: ResponsavelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ haras }) => {
      this.updateForm(haras);

      this.responsavelService
        .query()
        .pipe(
          map((res: HttpResponse<IResponsavel[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IResponsavel[]) => (this.responsavels = resBody));
    });
  }

  updateForm(haras: IHaras): void {
    this.editForm.patchValue({
      id: haras.id,
      nome: haras.nome,
      localidade: haras.localidade,
      uf: haras.uf,
      responsavel: haras.responsavel
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
      responsavel: this.editForm.get(['responsavel'])!.value
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

  trackById(index: number, item: IResponsavel): any {
    return item.id;
  }
}
