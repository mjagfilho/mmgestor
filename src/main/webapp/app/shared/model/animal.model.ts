import { Moment } from 'moment';
import { IDadosAssociacao } from 'app/shared/model/dados-associacao.model';
import { Sexo } from 'app/shared/model/enumerations/sexo.model';
import { Pelagem } from 'app/shared/model/enumerations/pelagem.model';

export interface IAnimal {
  id?: number;
  nome?: string;
  dtNascimento?: Moment;
  sexo?: Sexo;
  pelagem?: Pelagem;
  ehVivo?: boolean;
  dadosAssociacao?: IDadosAssociacao;
}

export class Animal implements IAnimal {
  constructor(
    public id?: number,
    public nome?: string,
    public dtNascimento?: Moment,
    public sexo?: Sexo,
    public pelagem?: Pelagem,
    public ehVivo?: boolean,
    public dadosAssociacao?: IDadosAssociacao
  ) {
    this.ehVivo = this.ehVivo || false;
  }
}
