import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { HarasUpdateComponent } from 'app/entities/haras/haras-update.component';
import { HarasService } from 'app/entities/haras/haras.service';
import { Haras } from 'app/shared/model/haras.model';

describe('Component Tests', () => {
  describe('Haras Management Update Component', () => {
    let comp: HarasUpdateComponent;
    let fixture: ComponentFixture<HarasUpdateComponent>;
    let service: HarasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [HarasUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(HarasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HarasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HarasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Haras(123);
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
        const entity = new Haras();
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
