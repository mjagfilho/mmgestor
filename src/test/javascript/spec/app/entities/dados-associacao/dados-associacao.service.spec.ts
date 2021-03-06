import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DadosAssociacaoService } from 'app/entities/dados-associacao/dados-associacao.service';
import { IDadosAssociacao, DadosAssociacao } from 'app/shared/model/dados-associacao.model';

describe('Service Tests', () => {
  describe('DadosAssociacao Service', () => {
    let injector: TestBed;
    let service: DadosAssociacaoService;
    let httpMock: HttpTestingController;
    let elemDefault: IDadosAssociacao;
    let expectedResult: IDadosAssociacao | IDadosAssociacao[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DadosAssociacaoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DadosAssociacao(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DadosAssociacao', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DadosAssociacao()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DadosAssociacao', () => {
        const returnedFromService = Object.assign(
          {
            criador: 'BBBBBB',
            proprietario: 'BBBBBB',
            livro: 'BBBBBB',
            registro: 'BBBBBB',
            exameDNA: 'BBBBBB',
            chip: 'BBBBBB',
            ehBloqueado: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DadosAssociacao', () => {
        const returnedFromService = Object.assign(
          {
            criador: 'BBBBBB',
            proprietario: 'BBBBBB',
            livro: 'BBBBBB',
            registro: 'BBBBBB',
            exameDNA: 'BBBBBB',
            chip: 'BBBBBB',
            ehBloqueado: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DadosAssociacao', () => {
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
