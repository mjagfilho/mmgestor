import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { ResponsavelDetailComponent } from 'app/entities/responsavel/responsavel-detail.component';
import { Responsavel } from 'app/shared/model/responsavel.model';

describe('Component Tests', () => {
  describe('Responsavel Management Detail Component', () => {
    let comp: ResponsavelDetailComponent;
    let fixture: ComponentFixture<ResponsavelDetailComponent>;
    const route = ({ data: of({ responsavel: new Responsavel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [ResponsavelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResponsavelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResponsavelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load responsavel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.responsavel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
