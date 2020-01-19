import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { MmGestorTestModule } from '../../../test.module';
import { DadosAssociacaoComponent } from 'app/entities/dados-associacao/dados-associacao.component';
import { DadosAssociacaoService } from 'app/entities/dados-associacao/dados-associacao.service';
import { DadosAssociacao } from 'app/shared/model/dados-associacao.model';

describe('Component Tests', () => {
  describe('DadosAssociacao Management Component', () => {
    let comp: DadosAssociacaoComponent;
    let fixture: ComponentFixture<DadosAssociacaoComponent>;
    let service: DadosAssociacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmGestorTestModule],
        declarations: [DadosAssociacaoComponent],
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
        .overrideTemplate(DadosAssociacaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DadosAssociacaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DadosAssociacaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DadosAssociacao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dadosAssociacaos && comp.dadosAssociacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DadosAssociacao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dadosAssociacaos && comp.dadosAssociacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
