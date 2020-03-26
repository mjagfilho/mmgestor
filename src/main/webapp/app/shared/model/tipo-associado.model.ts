export interface ITipoAssociado {
  id?: number;
  nome?: string;
  descricao?: string;
  ehAdministrador?: boolean;
  ehFinanceiro?: boolean;
  ehOperacional?: boolean;
  ehVeterinario?: boolean;
}

export class TipoAssociado implements ITipoAssociado {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public ehAdministrador?: boolean,
    public ehFinanceiro?: boolean,
    public ehOperacional?: boolean,
    public ehVeterinario?: boolean
  ) {
    this.ehAdministrador = this.ehAdministrador || false;
    this.ehFinanceiro = this.ehFinanceiro || false;
    this.ehOperacional = this.ehOperacional || false;
    this.ehVeterinario = this.ehVeterinario || false;
  }
}
