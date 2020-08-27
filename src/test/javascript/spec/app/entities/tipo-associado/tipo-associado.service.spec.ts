import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { TipoAssociadoService } from 'app/entities/tipo-associado/tipo-associado.service';
import { ITipoAssociado, TipoAssociado } from 'app/shared/model/tipo-associado.model';

describe('Service Tests', () => {
  describe('TipoAssociado Service', () => {
    let injector: TestBed;
    let service: TipoAssociadoService;
    let httpMock: HttpTestingController;
    let elemDefault: ITipoAssociado;
    let expectedResult: ITipoAssociado | ITipoAssociado[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TipoAssociadoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TipoAssociado(0, 'AAAAAAA', 'AAAAAAA', false, false, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TipoAssociado', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new TipoAssociado())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TipoAssociado', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            ehAdministrador: true,
            ehFinanceiro: true,
            ehOperacional: true,
            ehVeterinario: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TipoAssociado', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            ehAdministrador: true,
            ehFinanceiro: true,
            ehOperacional: true,
            ehVeterinario: true
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TipoAssociado', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
