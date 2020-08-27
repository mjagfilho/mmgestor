import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { AssociadosHarasDetailComponent } from 'app/entities/associados-haras/associados-haras-detail.component';
import { AssociadosHaras } from 'app/shared/model/associados-haras.model';

describe('Component Tests', () => {
  describe('AssociadosHaras Management Detail Component', () => {
    let comp: AssociadosHarasDetailComponent;
    let fixture: ComponentFixture<AssociadosHarasDetailComponent>;
    const route = ({ data: of({ associadosHaras: new AssociadosHaras(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [AssociadosHarasDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AssociadosHarasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssociadosHarasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load associadosHaras on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.associadosHaras).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
