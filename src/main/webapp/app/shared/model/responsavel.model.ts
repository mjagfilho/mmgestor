import { Moment } from 'moment';
import { IEndereco } from 'app/shared/model/endereco.model';
import { IUser } from 'app/core/user/user.model';

export interface IResponsavel {
  id?: number;
  cpf?: string;
  nomeCompleto?: string;
  dtNascimento?: Moment;
  endereco?: IEndereco;
  usuario?: IUser;
}

export class Responsavel implements IResponsavel {
  constructor(
    public id?: number,
    public cpf?: string,
    public nomeCompleto?: string,
    public dtNascimento?: Moment,
    public endereco?: IEndereco,
    public usuario?: IUser
  ) {}
}
