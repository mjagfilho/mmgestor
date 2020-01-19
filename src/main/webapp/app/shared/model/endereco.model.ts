export interface IEndereco {
  id?: number;
  cep?: number;
  logradouro?: string;
  complemento?: string;
  bairro?: string;
  localidade?: string;
  uf?: string;
}

export class Endereco implements IEndereco {
  constructor(
    public id?: number,
    public cep?: number,
    public logradouro?: string,
    public complemento?: string,
    public bairro?: string,
    public localidade?: string,
    public uf?: string
  ) {}
}
