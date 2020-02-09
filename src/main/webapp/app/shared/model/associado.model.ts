import { Moment } from 'moment';
import { IEndereco } from 'app/shared/model/endereco.model';
import { IUser } from 'app/core/user/user.model';

export interface IAssociado {
  id?: number;
  dtNascimento?: Moment;
  endereco?: IEndereco;
  usuario?: IUser;
}

export class Associado implements IAssociado {
  constructor(public id?: number, public dtNascimento?: Moment, public endereco?: IEndereco, public usuario?: IUser) {}
}
