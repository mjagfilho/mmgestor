import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { AssociadoDetailComponent } from 'app/entities/associado/associado-detail.component';
import { Associado } from 'app/shared/model/associado.model';

describe('Component Tests', () => {
  describe('Associado Management Detail Component', () => {
    let comp: AssociadoDetailComponent;
    let fixture: ComponentFixture<AssociadoDetailComponent>;
    const route = ({ data: of({ associado: new Associado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [AssociadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AssociadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssociadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load associado on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.associado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
