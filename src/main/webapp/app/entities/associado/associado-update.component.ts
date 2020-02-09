import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IAssociado, Associado } from 'app/shared/model/associado.model';
import { AssociadoService } from './associado.service';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco/endereco.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IEndereco | IUser;

@Component({
  selector: 'jhi-associado-update',
  templateUrl: './associado-update.component.html'
})
export class AssociadoUpdateComponent implements OnInit {
  isSaving = false;

  enderecos: IEndereco[] = [];

  users: IUser[] = [];
  dtNascimentoDp: any;

  editForm = this.fb.group({
    id: [],
    nomeCompleto: [null, [Validators.required]],
    dtNascimento: [],
    endereco: [null, Validators.required],
    usuario: [null, Validators.required]
  });

  constructor(
    protected associadoService: AssociadoService,
    protected enderecoService: EnderecoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ associado }) => {
      this.updateForm(associado);

      this.enderecoService
        .query({ filter: 'associado-is-null' })
        .pipe(
          map((res: HttpResponse<IEndereco[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEndereco[]) => {
          if (!associado.endereco || !associado.endereco.id) {
            this.enderecos = resBody;
          } else {
            this.enderecoService
              .find(associado.endereco.id)
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

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));
    });
  }

  updateForm(associado: IAssociado): void {
    this.editForm.patchValue({
      id: associado.id,
      nomeCompleto: associado.nomeCompleto,
      dtNascimento: associado.dtNascimento,
      endereco: associado.endereco,
      usuario: associado.usuario
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
      nomeCompleto: this.editForm.get(['nomeCompleto'])!.value,
      dtNascimento: this.editForm.get(['dtNascimento'])!.value,
      endereco: this.editForm.get(['endereco'])!.value,
      usuario: this.editForm.get(['usuario'])!.value
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
