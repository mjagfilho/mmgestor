import { Moment } from 'moment';

export interface IClienteFornecedor {
  id?: number;
  nome?: string;
  dtNascimento?: Moment;
  cpf?: string;
  nomeHaras?: string;
  localidadeHaras?: string;
  ufHaras?: string;
  cep?: string;
  logradouro?: string;
  numero?: string;
  complemento?: string;
  bairro?: string;
  localidade?: string;
  uf?: string;
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
    public cep?: string,
    public logradouro?: string,
    public numero?: string,
    public complemento?: string,
    public bairro?: string,
    public localidade?: string,
    public uf?: string
  ) {}
}
