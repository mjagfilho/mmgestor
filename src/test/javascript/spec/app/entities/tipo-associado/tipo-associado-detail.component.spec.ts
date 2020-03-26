import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { TipoAssociadoDetailComponent } from 'app/entities/tipo-associado/tipo-associado-detail.component';
import { TipoAssociado } from 'app/shared/model/tipo-associado.model';

describe('Component Tests', () => {
  describe('TipoAssociado Management Detail Component', () => {
    let comp: TipoAssociadoDetailComponent;
    let fixture: ComponentFixture<TipoAssociadoDetailComponent>;
    const route = ({ data: of({ tipoAssociado: new TipoAssociado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [TipoAssociadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoAssociadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoAssociadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoAssociado on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoAssociado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
