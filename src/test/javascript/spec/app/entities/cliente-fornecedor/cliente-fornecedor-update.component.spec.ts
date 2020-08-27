import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { ClienteFornecedorUpdateComponent } from 'app/entities/cliente-fornecedor/cliente-fornecedor-update.component';
import { ClienteFornecedorService } from 'app/entities/cliente-fornecedor/cliente-fornecedor.service';
import { ClienteFornecedor } from 'app/shared/model/cliente-fornecedor.model';

describe('Component Tests', () => {
  describe('ClienteFornecedor Management Update Component', () => {
    let comp: ClienteFornecedorUpdateComponent;
    let fixture: ComponentFixture<ClienteFornecedorUpdateComponent>;
    let service: ClienteFornecedorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [ClienteFornecedorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ClienteFornecedorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClienteFornecedorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClienteFornecedorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClienteFornecedor(123);
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
        const entity = new ClienteFornecedor();
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
