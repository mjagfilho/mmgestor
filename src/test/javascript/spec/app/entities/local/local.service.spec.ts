import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LocalService } from 'app/entities/local/local.service';
import { ILocal, Local } from 'app/shared/model/local.model';

describe('Service Tests', () => {
  describe('Local Service', () => {
    let injector: TestBed;
    let service: LocalService;
    let httpMock: HttpTestingController;
    let elemDefault: ILocal;
    let expectedResult: ILocal | ILocal[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LocalService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Local(0, 'AAAAAAA', 0, false, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Local', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Local()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Local', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            area: 1,
            ehContigua: true,
            cep: 'BBBBBB',
            logradouro: 'BBBBBB',
            numero: 'BBBBBB',
            complemento: 'BBBBBB',
            bairro: 'BBBBBB',
            localidade: 'BBBBBB',
            uf: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Local', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            area: 1,
            ehContigua: true,
            cep: 'BBBBBB',
            logradouro: 'BBBBBB',
            numero: 'BBBBBB',
            complemento: 'BBBBBB',
            bairro: 'BBBBBB',
            localidade: 'BBBBBB',
            uf: 'BBBBBB',
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

      it('should delete a Local', () => {
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
