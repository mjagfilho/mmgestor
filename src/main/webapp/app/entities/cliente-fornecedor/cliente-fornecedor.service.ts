import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClienteFornecedor } from 'app/shared/model/cliente-fornecedor.model';

type EntityResponseType = HttpResponse<IClienteFornecedor>;
type EntityArrayResponseType = HttpResponse<IClienteFornecedor[]>;

@Injectable({ providedIn: 'root' })
export class ClienteFornecedorService {
  public resourceUrl = SERVER_API_URL + 'api/cliente-fornecedors';

  constructor(protected http: HttpClient) {}

  create(clienteFornecedor: IClienteFornecedor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(clienteFornecedor);
    return this.http
      .post<IClienteFornecedor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(clienteFornecedor: IClienteFornecedor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(clienteFornecedor);
    return this.http
      .put<IClienteFornecedor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClienteFornecedor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClienteFornecedor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(clienteFornecedor: IClienteFornecedor): IClienteFornecedor {
    const copy: IClienteFornecedor = Object.assign({}, clienteFornecedor, {
      dtNascimento:
        clienteFornecedor.dtNascimento && clienteFornecedor.dtNascimento.isValid() ? clienteFornecedor.dtNascimento.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dtNascimento = res.body.dtNascimento ? moment(res.body.dtNascimento) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((clienteFornecedor: IClienteFornecedor) => {
        clienteFornecedor.dtNascimento = clienteFornecedor.dtNascimento ? moment(clienteFornecedor.dtNascimento) : undefined;
      });
    }
    return res;
  }
}
