export interface ITipoLocal {
  id?: number;
  nome?: string;
  descricao?: string;
}

export class TipoLocal implements ITipoLocal {
  constructor(public id?: number, public nome?: string, public descricao?: string) {}
}
