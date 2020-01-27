import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { DadosAssociacaoDetailComponent } from 'app/entities/dados-associacao/dados-associacao-detail.component';
import { DadosAssociacao } from 'app/shared/model/dados-associacao.model';

describe('Component Tests', () => {
  describe('DadosAssociacao Management Detail Component', () => {
    let comp: DadosAssociacaoDetailComponent;
    let fixture: ComponentFixture<DadosAssociacaoDetailComponent>;
    const route = ({ data: of({ dadosAssociacao: new DadosAssociacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [DadosAssociacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DadosAssociacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DadosAssociacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dadosAssociacao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dadosAssociacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
