import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { TipoLocalUpdateComponent } from 'app/entities/tipo-local/tipo-local-update.component';
import { TipoLocalService } from 'app/entities/tipo-local/tipo-local.service';
import { TipoLocal } from 'app/shared/model/tipo-local.model';

describe('Component Tests', () => {
  describe('TipoLocal Management Update Component', () => {
    let comp: TipoLocalUpdateComponent;
    let fixture: ComponentFixture<TipoLocalUpdateComponent>;
    let service: TipoLocalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [TipoLocalUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TipoLocalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoLocalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoLocalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoLocal(123);
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
        const entity = new TipoLocal();
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
