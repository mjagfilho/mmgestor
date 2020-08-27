import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAssociadosHaras } from 'app/shared/model/associados-haras.model';

type EntityResponseType = HttpResponse<IAssociadosHaras>;
type EntityArrayResponseType = HttpResponse<IAssociadosHaras[]>;

@Injectable({ providedIn: 'root' })
export class AssociadosHarasService {
  public resourceUrl = SERVER_API_URL + 'api/associados-haras';

  constructor(protected http: HttpClient) {}

  create(associadosHaras: IAssociadosHaras): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(associadosHaras);
    return this.http
      .post<IAssociadosHaras>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(associadosHaras: IAssociadosHaras): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(associadosHaras);
    return this.http
      .put<IAssociadosHaras>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAssociadosHaras>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssociadosHaras[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(associadosHaras: IAssociadosHaras): IAssociadosHaras {
    const copy: IAssociadosHaras = Object.assign({}, associadosHaras, {
      dataAssociacao:
        associadosHaras.dataAssociacao && associadosHaras.dataAssociacao.isValid()
          ? associadosHaras.dataAssociacao.format(DATE_FORMAT)
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataAssociacao = res.body.dataAssociacao ? moment(res.body.dataAssociacao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((associadosHaras: IAssociadosHaras) => {
        associadosHaras.dataAssociacao = associadosHaras.dataAssociacao ? moment(associadosHaras.dataAssociacao) : undefined;
      });
    }
    return res;
  }
}
