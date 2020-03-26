import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoAssociado } from 'app/shared/model/tipo-associado.model';

type EntityResponseType = HttpResponse<ITipoAssociado>;
type EntityArrayResponseType = HttpResponse<ITipoAssociado[]>;

@Injectable({ providedIn: 'root' })
export class TipoAssociadoService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-associados';

  constructor(protected http: HttpClient) {}

  create(tipoAssociado: ITipoAssociado): Observable<EntityResponseType> {
    return this.http.post<ITipoAssociado>(this.resourceUrl, tipoAssociado, { observe: 'response' });
  }

  update(tipoAssociado: ITipoAssociado): Observable<EntityResponseType> {
    return this.http.put<ITipoAssociado>(this.resourceUrl, tipoAssociado, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoAssociado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoAssociado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
