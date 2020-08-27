import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { DadosAssociacaoUpdateComponent } from 'app/entities/dados-associacao/dados-associacao-update.component';
import { DadosAssociacaoService } from 'app/entities/dados-associacao/dados-associacao.service';
import { DadosAssociacao } from 'app/shared/model/dados-associacao.model';

describe('Component Tests', () => {
  describe('DadosAssociacao Management Update Component', () => {
    let comp: DadosAssociacaoUpdateComponent;
    let fixture: ComponentFixture<DadosAssociacaoUpdateComponent>;
    let service: DadosAssociacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [DadosAssociacaoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DadosAssociacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DadosAssociacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DadosAssociacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DadosAssociacao(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DadosAssociacao();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
