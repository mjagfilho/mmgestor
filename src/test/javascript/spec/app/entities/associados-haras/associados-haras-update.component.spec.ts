import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { AssociadosHarasUpdateComponent } from 'app/entities/associados-haras/associados-haras-update.component';
import { AssociadosHarasService } from 'app/entities/associados-haras/associados-haras.service';
import { AssociadosHaras } from 'app/shared/model/associados-haras.model';

describe('Component Tests', () => {
  describe('AssociadosHaras Management Update Component', () => {
    let comp: AssociadosHarasUpdateComponent;
    let fixture: ComponentFixture<AssociadosHarasUpdateComponent>;
    let service: AssociadosHarasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [AssociadosHarasUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AssociadosHarasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssociadosHarasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssociadosHarasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AssociadosHaras(123);
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
        const entity = new AssociadosHaras();
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
