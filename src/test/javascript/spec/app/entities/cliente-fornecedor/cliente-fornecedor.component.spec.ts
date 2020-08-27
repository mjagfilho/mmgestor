import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { MmgestorTestModule } from '../../../test.module';
import { ClienteFornecedorComponent } from 'app/entities/cliente-fornecedor/cliente-fornecedor.component';
import { ClienteFornecedorService } from 'app/entities/cliente-fornecedor/cliente-fornecedor.service';
import { ClienteFornecedor } from 'app/shared/model/cliente-fornecedor.model';

describe('Component Tests', () => {
  describe('ClienteFornecedor Management Component', () => {
    let comp: ClienteFornecedorComponent;
    let fixture: ComponentFixture<ClienteFornecedorComponent>;
    let service: ClienteFornecedorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [ClienteFornecedorComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(ClienteFornecedorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClienteFornecedorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClienteFornecedorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ClienteFornecedor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.clienteFornecedors && comp.clienteFornecedors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ClienteFornecedor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.clienteFornecedors && comp.clienteFornecedors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
