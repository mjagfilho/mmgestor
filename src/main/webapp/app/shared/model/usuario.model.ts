export interface IUsuario {
  id?: number;
  email?: string;
  senha?: string;
}

export class Usuario implements IUsuario {
  constructor(public id?: number, public email?: string, public senha?: string) {}
}
