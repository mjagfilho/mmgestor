import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { TipoAssociadoUpdateComponent } from 'app/entities/tipo-associado/tipo-associado-update.component';
import { TipoAssociadoService } from 'app/entities/tipo-associado/tipo-associado.service';
import { TipoAssociado } from 'app/shared/model/tipo-associado.model';

describe('Component Tests', () => {
  describe('TipoAssociado Management Update Component', () => {
    let comp: TipoAssociadoUpdateComponent;
    let fixture: ComponentFixture<TipoAssociadoUpdateComponent>;
    let service: TipoAssociadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [TipoAssociadoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoAssociadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoAssociadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoAssociadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoAssociado(123);
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
        const entity = new TipoAssociado();
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
