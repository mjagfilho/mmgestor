import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IResponsavel } from 'app/shared/model/responsavel.model';

type EntityResponseType = HttpResponse<IResponsavel>;
type EntityArrayResponseType = HttpResponse<IResponsavel[]>;

@Injectable({ providedIn: 'root' })
export class ResponsavelService {
  public resourceUrl = SERVER_API_URL + 'api/responsavels';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/responsavels';

  constructor(protected http: HttpClient) {}

  create(responsavel: IResponsavel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavel);
    return this.http
      .post<IResponsavel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(responsavel: IResponsavel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavel);
    return this.http
      .put<IResponsavel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IResponsavel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResponsavel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResponsavel[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(responsavel: IResponsavel): IResponsavel {
    const copy: IResponsavel = Object.assign({}, responsavel, {
      dtNascimento: responsavel.dtNascimento && responsavel.dtNascimento.isValid() ? responsavel.dtNascimento.toJSON() : undefined
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
      res.body.forEach((responsavel: IResponsavel) => {
        responsavel.dtNascimento = responsavel.dtNascimento ? moment(responsavel.dtNascimento) : undefined;
      });
    }
    return res;
  }
}
