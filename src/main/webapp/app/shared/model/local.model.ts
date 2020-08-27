import { ITipoLocal } from 'app/shared/model/tipo-local.model';
import { ILocal } from 'app/shared/model/local.model';

export interface ILocal {
  id?: number;
  nome?: string;
  area?: number;
  ehContigua?: boolean;
  cep?: string;
  logradouro?: string;
  numero?: string;
  complemento?: string;
  bairro?: string;
  localidade?: string;
  uf?: string;
  tipo?: ITipoLocal;
  pai?: ILocal;
}

export class Local implements ILocal {
  constructor(
    public id?: number,
    public nome?: string,
    public area?: number,
    public ehContigua?: boolean,
    public cep?: string,
    public logradouro?: string,
    public numero?: string,
    public complemento?: string,
    public bairro?: string,
    public localidade?: string,
    public uf?: string,
    public tipo?: ITipoLocal,
    public pai?: ILocal
  ) {
    this.ehContigua = this.ehContigua || false;
  }
}
