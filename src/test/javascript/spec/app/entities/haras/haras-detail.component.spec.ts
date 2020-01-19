import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MmGestorTestModule } from '../../../test.module';
import { HarasDetailComponent } from 'app/entities/haras/haras-detail.component';
import { Haras } from 'app/shared/model/haras.model';

describe('Component Tests', () => {
  describe('Haras Management Detail Component', () => {
    let comp: HarasDetailComponent;
    let fixture: ComponentFixture<HarasDetailComponent>;
    const route = ({ data: of({ haras: new Haras(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmGestorTestModule],
        declarations: [HarasDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HarasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HarasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load haras on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.haras).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
