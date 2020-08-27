import { Moment } from 'moment';
import { IAssociado } from 'app/shared/model/associado.model';
import { IHaras } from 'app/shared/model/haras.model';

export interface IAssociadosHaras {
  id?: number;
  dataAssociacao?: Moment;
  ehAtivo?: boolean;
  associado?: IAssociado;
  haras?: IHaras;
}

export class AssociadosHaras implements IAssociadosHaras {
  constructor(
    public id?: number,
    public dataAssociacao?: Moment,
    public ehAtivo?: boolean,
    public associado?: IAssociado,
    public haras?: IHaras
  ) {
    this.ehAtivo = this.ehAtivo || false;
  }
}
