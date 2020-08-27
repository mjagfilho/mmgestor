import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { TipoLocalDetailComponent } from 'app/entities/tipo-local/tipo-local-detail.component';
import { TipoLocal } from 'app/shared/model/tipo-local.model';

describe('Component Tests', () => {
  describe('TipoLocal Management Detail Component', () => {
    let comp: TipoLocalDetailComponent;
    let fixture: ComponentFixture<TipoLocalDetailComponent>;
    const route = ({ data: of({ tipoLocal: new TipoLocal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [TipoLocalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TipoLocalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoLocalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoLocal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoLocal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
