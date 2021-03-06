import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { DadosAssociacaoService } from 'app/entities/dados-associacao/dados-associacao.service';
import { Animal, IAnimal } from 'app/shared/model/animal.model';
import { IDadosAssociacao } from 'app/shared/model/dados-associacao.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnimalService } from './animal.service';

@Component({
  selector: 'jhi-animal-update',
  templateUrl: './animal-update.component.html',
})
export class AnimalUpdateComponent implements OnInit {
  isSaving = false;
  dadosassociacaos: IDadosAssociacao[] = [];
  dtNascimentoDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    dtNascimento: [null, [Validators.required]],
    sexo: [null, [Validators.required]],
    pelagem: [null, [Validators.required]],
    ehVivo: [null, [Validators.required]],
    dadosAssociacao: [],
  });

  constructor(
    protected animalService: AnimalService,
    protected dadosAssociacaoService: DadosAssociacaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ animal }) => {
      this.updateForm(animal);

      this.dadosAssociacaoService
        .query({ filter: 'animal-is-null' })
        .pipe(
          map((res: HttpResponse<IDadosAssociacao[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDadosAssociacao[]) => {
          if (!animal.dadosAssociacao || !animal.dadosAssociacao.id) {
            this.dadosassociacaos = resBody;
          } else {
            this.dadosAssociacaoService
              .find(animal.dadosAssociacao.id)
              .pipe(
                map((subRes: HttpResponse<IDadosAssociacao>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDadosAssociacao[]) => (this.dadosassociacaos = concatRes));
          }
        });
    });
  }

  updateForm(animal: IAnimal): void {
    this.editForm.patchValue({
      id: animal.id,
      nome: animal.nome,
      dtNascimento: animal.dtNascimento,
      sexo: animal.sexo,
      pelagem: animal.pelagem,
      ehVivo: animal.ehVivo,
      dadosAssociacao: animal.dadosAssociacao,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const animal = this.createFromForm();
    if (animal.id !== undefined) {
      this.subscribeToSaveResponse(this.animalService.update(animal));
    } else {
      this.subscribeToSaveResponse(this.animalService.create(animal));
    }
  }

  private createFromForm(): IAnimal {
    return {
      ...new Animal(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      dtNascimento: this.editForm.get(['dtNascimento'])!.value,
      sexo: this.editForm.get(['sexo'])!.value,
      pelagem: this.editForm.get(['pelagem'])!.value,
      ehVivo: this.editForm.get(['ehVivo'])!.value,
      dadosAssociacao: this.editForm.get(['dadosAssociacao'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnimal>>): void {
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

  trackById(index: number, item: IDadosAssociacao): any {
    return item.id;
  }
}
