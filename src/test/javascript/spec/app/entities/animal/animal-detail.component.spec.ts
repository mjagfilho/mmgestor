import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { AnimalDetailComponent } from 'app/entities/animal/animal-detail.component';
import { Animal } from 'app/shared/model/animal.model';

describe('Component Tests', () => {
  describe('Animal Management Detail Component', () => {
    let comp: AnimalDetailComponent;
    let fixture: ComponentFixture<AnimalDetailComponent>;
    const route = ({ data: of({ animal: new Animal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [AnimalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnimalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnimalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load animal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.animal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
