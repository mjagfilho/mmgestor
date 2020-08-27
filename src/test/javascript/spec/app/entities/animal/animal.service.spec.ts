import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AnimalService } from 'app/entities/animal/animal.service';
import { IAnimal, Animal } from 'app/shared/model/animal.model';
import { Sexo } from 'app/shared/model/enumerations/sexo.model';
import { Pelagem } from 'app/shared/model/enumerations/pelagem.model';

describe('Service Tests', () => {
  describe('Animal Service', () => {
    let injector: TestBed;
    let service: AnimalService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnimal;
    let expectedResult: IAnimal | IAnimal[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AnimalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Animal(0, 'AAAAAAA', currentDate, Sexo.MACHO, Pelagem.BRANCA, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dtNascimento: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Animal', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dtNascimento: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dtNascimento: currentDate,
          },
          returnedFromService
        );

        service.create(new Animal()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Animal', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            dtNascimento: currentDate.format(DATE_FORMAT),
            sexo: 'BBBBBB',
            pelagem: 'BBBBBB',
            ehVivo: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dtNascimento: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Animal', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            dtNascimento: currentDate.format(DATE_FORMAT),
            sexo: 'BBBBBB',
            pelagem: 'BBBBBB',
            ehVivo: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dtNascimento: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Animal', () => {
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
