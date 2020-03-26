import { Moment } from 'moment';
import { ITipoAssociado } from 'app/shared/model/tipo-associado.model';
import { IEndereco } from 'app/shared/model/endereco.model';
import { IUser } from 'app/core/user/user.model';

export interface IAssociado {
  id?: number;
  dtNascimento?: Moment;
  tipo?: ITipoAssociado;
  endereco?: IEndereco;
  usuario?: IUser;
}

export class Associado implements IAssociado {
  constructor(
    public id?: number,
    public dtNascimento?: Moment,
    public tipo?: ITipoAssociado,
    public endereco?: IEndereco,
    public usuario?: IUser
  ) {}
}
