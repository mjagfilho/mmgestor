import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AssociadosHarasService } from 'app/entities/associados-haras/associados-haras.service';
import { IAssociadosHaras, AssociadosHaras } from 'app/shared/model/associados-haras.model';

describe('Service Tests', () => {
  describe('AssociadosHaras Service', () => {
    let injector: TestBed;
    let service: AssociadosHarasService;
    let httpMock: HttpTestingController;
    let elemDefault: IAssociadosHaras;
    let expectedResult: IAssociadosHaras | IAssociadosHaras[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AssociadosHarasService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AssociadosHaras(0, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataAssociacao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AssociadosHaras', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataAssociacao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataAssociacao: currentDate
          },
          returnedFromService
        );

        service.create(new AssociadosHaras()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AssociadosHaras', () => {
        const returnedFromService = Object.assign(
          {
            dataAssociacao: currentDate.format(DATE_FORMAT),
            ehAtivo: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataAssociacao: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AssociadosHaras', () => {
        const returnedFromService = Object.assign(
          {
            dataAssociacao: currentDate.format(DATE_FORMAT),
            ehAtivo: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataAssociacao: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AssociadosHaras', () => {
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
