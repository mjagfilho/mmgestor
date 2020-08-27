import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAssociado, Associado } from 'app/shared/model/associado.model';
import { AssociadoService } from './associado.service';
import { ITipoAssociado } from 'app/shared/model/tipo-associado.model';
import { TipoAssociadoService } from 'app/entities/tipo-associado/tipo-associado.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = ITipoAssociado | IUser;

@Component({
  selector: 'jhi-associado-update',
  templateUrl: './associado-update.component.html',
})
export class AssociadoUpdateComponent implements OnInit {
  isSaving = false;
  tipos: ITipoAssociado[] = [];
  users: IUser[] = [];
  dtNascimentoDp: any;

  editForm = this.fb.group({
    id: [],
    dtNascimento: [],
    cep: [null, [Validators.required, Validators.minLength(9), Validators.maxLength(9), Validators.pattern('[0-9]{5}-[0-9]{3}')]],
    logradouro: [null, [Validators.required]],
    numero: [null, [Validators.required]],
    complemento: [],
    bairro: [null, [Validators.required]],
    localidade: [null, [Validators.required]],
    uf: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(2), Validators.pattern('[A-Z]{2}')]],
    tipo: [null, Validators.required],
    usuario: [null, Validators.required],
  });

  constructor(
    protected associadoService: AssociadoService,
    protected tipoAssociadoService: TipoAssociadoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ associado }) => {
      this.updateForm(associado);

      this.tipoAssociadoService
        .query({ filter: 'associado-is-null' })
        .pipe(
          map((res: HttpResponse<ITipoAssociado[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITipoAssociado[]) => {
          if (!associado.tipo || !associado.tipo.id) {
            this.tipos = resBody;
          } else {
            this.tipoAssociadoService
              .find(associado.tipo.id)
              .pipe(
                map((subRes: HttpResponse<ITipoAssociado>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITipoAssociado[]) => (this.tipos = concatRes));
          }
        });

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(associado: IAssociado): void {
    this.editForm.patchValue({
      id: associado.id,
      dtNascimento: associado.dtNascimento,
      cep: associado.cep,
      logradouro: associado.logradouro,
      numero: associado.numero,
      complemento: associado.complemento,
      bairro: associado.bairro,
      localidade: associado.localidade,
      uf: associado.uf,
      tipo: associado.tipo,
      usuario: associado.usuario,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const associado = this.createFromForm();
    if (associado.id !== undefined) {
      this.subscribeToSaveResponse(this.associadoService.update(associado));
    } else {
      this.subscribeToSaveResponse(this.associadoService.create(associado));
    }
  }

  private createFromForm(): IAssociado {
    return {
      ...new Associado(),
      id: this.editForm.get(['id'])!.value,
      dtNascimento: this.editForm.get(['dtNascimento'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      logradouro: this.editForm.get(['logradouro'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      complemento: this.editForm.get(['complemento'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      localidade: this.editForm.get(['localidade'])!.value,
      uf: this.editForm.get(['uf'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      usuario: this.editForm.get(['usuario'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssociado>>): void {
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
