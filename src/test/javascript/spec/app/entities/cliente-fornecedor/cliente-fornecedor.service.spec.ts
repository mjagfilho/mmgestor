import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ClienteFornecedorService } from 'app/entities/cliente-fornecedor/cliente-fornecedor.service';
import { IClienteFornecedor, ClienteFornecedor } from 'app/shared/model/cliente-fornecedor.model';

describe('Service Tests', () => {
  describe('ClienteFornecedor Service', () => {
    let injector: TestBed;
    let service: ClienteFornecedorService;
    let httpMock: HttpTestingController;
    let elemDefault: IClienteFornecedor;
    let expectedResult: IClienteFornecedor | IClienteFornecedor[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ClienteFornecedorService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ClienteFornecedor(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dtNascimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ClienteFornecedor', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dtNascimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dtNascimento: currentDate
          },
          returnedFromService
        );
        service
          .create(new ClienteFornecedor())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ClienteFornecedor', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            dtNascimento: currentDate.format(DATE_FORMAT),
            cpf: 'BBBBBB',
            nomeHaras: 'BBBBBB',
            localidadeHaras: 'BBBBBB',
            ufHaras: 'BBBBBB',
            cep: 'BBBBBB',
            logradouro: 'BBBBBB',
            numero: 'BBBBBB',
            complemento: 'BBBBBB',
            bairro: 'BBBBBB',
            localidade: 'BBBBBB',
            uf: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dtNascimento: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ClienteFornecedor', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            dtNascimento: currentDate.format(DATE_FORMAT),
            cpf: 'BBBBBB',
            nomeHaras: 'BBBBBB',
            localidadeHaras: 'BBBBBB',
            ufHaras: 'BBBBBB',
            cep: 'BBBBBB',
            logradouro: 'BBBBBB',
            numero: 'BBBBBB',
            complemento: 'BBBBBB',
            bairro: 'BBBBBB',
            localidade: 'BBBBBB',
            uf: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dtNascimento: currentDate
          },
          returnedFromService
        );
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

      it('should delete a ClienteFornecedor', () => {
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
