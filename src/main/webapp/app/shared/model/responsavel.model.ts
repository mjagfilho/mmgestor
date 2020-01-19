import { Moment } from 'moment';
import { IEndereco } from 'app/shared/model/endereco.model';
import { IUsuario } from 'app/shared/model/usuario.model';

export interface IResponsavel {
  id?: number;
  cpf?: number;
  nomeCompleto?: string;
  dtNascimento?: Moment;
  endereco?: IEndereco;
  usuario?: IUsuario;
}

export class Responsavel implements IResponsavel {
  constructor(
    public id?: number,
    public cpf?: number,
    public nomeCompleto?: string,
    public dtNascimento?: Moment,
    public endereco?: IEndereco,
    public usuario?: IUsuario
  ) {}
}
