import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoLocal } from 'app/shared/model/tipo-local.model';

type EntityResponseType = HttpResponse<ITipoLocal>;
type EntityArrayResponseType = HttpResponse<ITipoLocal[]>;

@Injectable({ providedIn: 'root' })
export class TipoLocalService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-locals';

  constructor(protected http: HttpClient) {}

  create(tipoLocal: ITipoLocal): Observable<EntityResponseType> {
    return this.http.post<ITipoLocal>(this.resourceUrl, tipoLocal, { observe: 'response' });
  }

  update(tipoLocal: ITipoLocal): Observable<EntityResponseType> {
    return this.http.put<ITipoLocal>(this.resourceUrl, tipoLocal, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoLocal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoLocal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
