import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { AssociadoUpdateComponent } from 'app/entities/associado/associado-update.component';
import { AssociadoService } from 'app/entities/associado/associado.service';
import { Associado } from 'app/shared/model/associado.model';

describe('Component Tests', () => {
  describe('Associado Management Update Component', () => {
    let comp: AssociadoUpdateComponent;
    let fixture: ComponentFixture<AssociadoUpdateComponent>;
    let service: AssociadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [AssociadoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AssociadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssociadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssociadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Associado(123);
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
        const entity = new Associado();
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
