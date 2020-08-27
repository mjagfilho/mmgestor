import { Moment } from 'moment';
import { ITipoAssociado } from 'app/shared/model/tipo-associado.model';
import { IUser } from 'app/core/user/user.model';

export interface IAssociado {
  id?: number;
  dtNascimento?: Moment;
  cep?: string;
  logradouro?: string;
  numero?: string;
  complemento?: string;
  bairro?: string;
  localidade?: string;
  uf?: string;
  tipo?: ITipoAssociado;
  usuario?: IUser;
}

export class Associado implements IAssociado {
  constructor(
    public id?: number,
    public dtNascimento?: Moment,
    public cep?: string,
    public logradouro?: string,
    public numero?: string,
    public complemento?: string,
    public bairro?: string,
    public localidade?: string,
    public uf?: string,
    public tipo?: ITipoAssociado,
    public usuario?: IUser
  ) {}
}
