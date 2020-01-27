import { Moment } from 'moment';
import { IEndereco } from 'app/shared/model/endereco.model';

export interface IClienteFornecedor {
  id?: number;
  nome?: string;
  dtNascimento?: Moment;
  cpf?: string;
  nomeHaras?: string;
  localidadeHaras?: string;
  ufHaras?: string;
  endereco?: IEndereco;
}

export class ClienteFornecedor implements IClienteFornecedor {
  constructor(
    public id?: number,
    public nome?: string,
    public dtNascimento?: Moment,
    public cpf?: string,
    public nomeHaras?: string,
    public localidadeHaras?: string,
    public ufHaras?: string,
    public endereco?: IEndereco
  ) {}
}
