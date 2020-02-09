import { IEndereco } from 'app/shared/model/endereco.model';
import { ITipoLocal } from 'app/shared/model/tipo-local.model';

export interface ILocal {
  id?: number;
  nome?: string;
  area?: number;
  ehContigua?: boolean;
  tipo?: ITipoLocal;
  endereco?: IEndereco;
  pai?: ILocal;
}

export class Local implements ILocal {
  constructor(
    public id?: number,
    public nome?: string,
    public area?: number,
    public ehContigua?: boolean,
    public tipo?: ITipoLocal,
    public endereco?: IEndereco,
    public pai?: ILocal
  ) {
    this.ehContigua = this.ehContigua || false;
  }
}
