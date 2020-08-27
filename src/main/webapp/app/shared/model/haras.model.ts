import { IAssociado } from 'app/shared/model/associado.model';

export interface IHaras {
  id?: number;
  nome?: string;
  localidade?: string;
  uf?: string;
  responsavel?: IAssociado;
}

export class Haras implements IHaras {
  constructor(public id?: number, public nome?: string, public localidade?: string, public uf?: string, public responsavel?: IAssociado) {}
}
