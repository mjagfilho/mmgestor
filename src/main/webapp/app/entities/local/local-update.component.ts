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
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco/endereco.service';

type SelectableEntity = ITipoLocal | IEndereco | ILocal;

@Component({
  selector: 'jhi-local-update',
  templateUrl: './local-update.component.html'
})
export class LocalUpdateComponent implements OnInit {
  isSaving = false;

  tipos: ITipoLocal[] = [];

  enderecos: IEndereco[] = [];

  locals: ILocal[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    area: [null, [Validators.required]],
    ehContigua: [null, [Validators.required]],
    tipo: [null, Validators.required],
    endereco: [null, Validators.required],
    pai: []
  });

  constructor(
    protected localService: LocalService,
    protected tipoLocalService: TipoLocalService,
    protected enderecoService: EnderecoService,
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
            return res.body ? res.body : [];
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
              .subscribe((concatRes: ITipoLocal[]) => {
                this.tipos = concatRes;
              });
          }
        });

      this.enderecoService
        .query({ filter: 'local-is-null' })
        .pipe(
          map((res: HttpResponse<IEndereco[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEndereco[]) => {
          if (!local.endereco || !local.endereco.id) {
            this.enderecos = resBody;
          } else {
            this.enderecoService
              .find(local.endereco.id)
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

      this.localService
        .query()
        .pipe(
          map((res: HttpResponse<ILocal[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ILocal[]) => (this.locals = resBody));
    });
  }

  updateForm(local: ILocal): void {
    this.editForm.patchValue({
      id: local.id,
      nome: local.nome,
      area: local.area,
      ehContigua: local.ehContigua,
      tipo: local.tipo,
      endereco: local.endereco,
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
      tipo: this.editForm.get(['tipo'])!.value,
      endereco: this.editForm.get(['endereco'])!.value,
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
