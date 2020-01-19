import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IResponsavel, Responsavel } from 'app/shared/model/responsavel.model';
import { ResponsavelService } from './responsavel.service';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco/endereco.service';
import { IUsuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from 'app/entities/usuario/usuario.service';

type SelectableEntity = IEndereco | IUsuario;

@Component({
  selector: 'jhi-responsavel-update',
  templateUrl: './responsavel-update.component.html'
})
export class ResponsavelUpdateComponent implements OnInit {
  isSaving = false;

  enderecos: IEndereco[] = [];

  usuarios: IUsuario[] = [];

  editForm = this.fb.group({
    id: [],
    cpf: [null, [Validators.required]],
    nomeCompleto: [null, [Validators.required]],
    dtNascimento: [],
    endereco: [null, Validators.required],
    usuario: [null, Validators.required]
  });

  constructor(
    protected responsavelService: ResponsavelService,
    protected enderecoService: EnderecoService,
    protected usuarioService: UsuarioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavel }) => {
      this.updateForm(responsavel);

      this.enderecoService
        .query({ filter: 'responsavel-is-null' })
        .pipe(
          map((res: HttpResponse<IEndereco[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEndereco[]) => {
          if (!responsavel.endereco || !responsavel.endereco.id) {
            this.enderecos = resBody;
          } else {
            this.enderecoService
              .find(responsavel.endereco.id)
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

      this.usuarioService
        .query({ filter: 'responsavel-is-null' })
        .pipe(
          map((res: HttpResponse<IUsuario[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUsuario[]) => {
          if (!responsavel.usuario || !responsavel.usuario.id) {
            this.usuarios = resBody;
          } else {
            this.usuarioService
              .find(responsavel.usuario.id)
              .pipe(
                map((subRes: HttpResponse<IUsuario>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IUsuario[]) => {
                this.usuarios = concatRes;
              });
          }
        });
    });
  }

  updateForm(responsavel: IResponsavel): void {
    this.editForm.patchValue({
      id: responsavel.id,
      cpf: responsavel.cpf,
      nomeCompleto: responsavel.nomeCompleto,
      dtNascimento: responsavel.dtNascimento != null ? responsavel.dtNascimento.format(DATE_TIME_FORMAT) : null,
      endereco: responsavel.endereco,
      usuario: responsavel.usuario
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const responsavel = this.createFromForm();
    if (responsavel.id !== undefined) {
      this.subscribeToSaveResponse(this.responsavelService.update(responsavel));
    } else {
      this.subscribeToSaveResponse(this.responsavelService.create(responsavel));
    }
  }

  private createFromForm(): IResponsavel {
    return {
      ...new Responsavel(),
      id: this.editForm.get(['id'])!.value,
      cpf: this.editForm.get(['cpf'])!.value,
      nomeCompleto: this.editForm.get(['nomeCompleto'])!.value,
      dtNascimento:
        this.editForm.get(['dtNascimento'])!.value != null
          ? moment(this.editForm.get(['dtNascimento'])!.value, DATE_TIME_FORMAT)
          : undefined,
      endereco: this.editForm.get(['endereco'])!.value,
      usuario: this.editForm.get(['usuario'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsavel>>): void {
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
