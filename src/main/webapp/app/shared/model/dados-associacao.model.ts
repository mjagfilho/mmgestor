import { IAnimal } from 'app/shared/model/animal.model';

export interface IDadosAssociacao {
  id?: number;
  criador?: string;
  proprietario?: string;
  livro?: string;
  registro?: string;
  exameDNA?: string;
  chip?: string;
  ehBloqueado?: boolean;
  animal?: IAnimal;
}

export class DadosAssociacao implements IDadosAssociacao {
  constructor(
    public id?: number,
    public criador?: string,
    public proprietario?: string,
    public livro?: string,
    public registro?: string,
    public exameDNA?: string,
    public chip?: string,
    public ehBloqueado?: boolean,
    public animal?: IAnimal
  ) {
    this.ehBloqueado = this.ehBloqueado || false;
  }
}
