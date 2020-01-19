import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IHaras } from 'app/shared/model/haras.model';

type EntityResponseType = HttpResponse<IHaras>;
type EntityArrayResponseType = HttpResponse<IHaras[]>;

@Injectable({ providedIn: 'root' })
export class HarasService {
  public resourceUrl = SERVER_API_URL + 'api/haras';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/haras';

  constructor(protected http: HttpClient) {}

  create(haras: IHaras): Observable<EntityResponseType> {
    return this.http.post<IHaras>(this.resourceUrl, haras, { observe: 'response' });
  }

  update(haras: IHaras): Observable<EntityResponseType> {
    return this.http.put<IHaras>(this.resourceUrl, haras, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHaras>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHaras[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHaras[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
