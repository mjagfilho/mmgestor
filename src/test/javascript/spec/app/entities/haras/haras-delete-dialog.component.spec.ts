import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MmgestorTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { HarasDeleteDialogComponent } from 'app/entities/haras/haras-delete-dialog.component';
import { HarasService } from 'app/entities/haras/haras.service';

describe('Component Tests', () => {
  describe('Haras Management Delete Component', () => {
    let comp: HarasDeleteDialogComponent;
    let fixture: ComponentFixture<HarasDeleteDialogComponent>;
    let service: HarasService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [HarasDeleteDialogComponent]
      })
        .overrideTemplate(HarasDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HarasDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HarasService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
