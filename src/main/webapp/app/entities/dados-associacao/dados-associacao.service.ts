import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IDadosAssociacao } from 'app/shared/model/dados-associacao.model';

type EntityResponseType = HttpResponse<IDadosAssociacao>;
type EntityArrayResponseType = HttpResponse<IDadosAssociacao[]>;

@Injectable({ providedIn: 'root' })
export class DadosAssociacaoService {
  public resourceUrl = SERVER_API_URL + 'api/dados-associacaos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/dados-associacaos';

  constructor(protected http: HttpClient) {}

  create(dadosAssociacao: IDadosAssociacao): Observable<EntityResponseType> {
    return this.http.post<IDadosAssociacao>(this.resourceUrl, dadosAssociacao, { observe: 'response' });
  }

  update(dadosAssociacao: IDadosAssociacao): Observable<EntityResponseType> {
    return this.http.put<IDadosAssociacao>(this.resourceUrl, dadosAssociacao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDadosAssociacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDadosAssociacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDadosAssociacao[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
