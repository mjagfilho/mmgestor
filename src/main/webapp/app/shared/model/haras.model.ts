import { IResponsavel } from 'app/shared/model/responsavel.model';

export interface IHaras {
  id?: number;
  nome?: string;
  localidade?: string;
  uf?: string;
  responsavel?: IResponsavel;
}

export class Haras implements IHaras {
  constructor(
    public id?: number,
    public nome?: string,
    public localidade?: string,
    public uf?: string,
    public responsavel?: IResponsavel
  ) {}
}
