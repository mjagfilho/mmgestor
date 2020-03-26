import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAssociado } from 'app/shared/model/associado.model';

type EntityResponseType = HttpResponse<IAssociado>;
type EntityArrayResponseType = HttpResponse<IAssociado[]>;

@Injectable({ providedIn: 'root' })
export class AssociadoService {
  public resourceUrl = SERVER_API_URL + 'api/associados';

  constructor(protected http: HttpClient) {}

  create(associado: IAssociado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(associado);
    return this.http
      .post<IAssociado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(associado: IAssociado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(associado);
    return this.http
      .put<IAssociado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAssociado>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssociado[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(associado: IAssociado): IAssociado {
    const copy: IAssociado = Object.assign({}, associado, {
      dtNascimento: associado.dtNascimento && associado.dtNascimento.isValid() ? associado.dtNascimento.format(DATE_FORMAT) : undefined
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
      res.body.forEach((associado: IAssociado) => {
        associado.dtNascimento = associado.dtNascimento ? moment(associado.dtNascimento) : undefined;
      });
    }
    return res;
  }
}
