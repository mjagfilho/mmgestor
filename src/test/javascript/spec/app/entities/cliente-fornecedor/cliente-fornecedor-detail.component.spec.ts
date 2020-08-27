import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MmgestorTestModule } from '../../../test.module';
import { ClienteFornecedorDetailComponent } from 'app/entities/cliente-fornecedor/cliente-fornecedor-detail.component';
import { ClienteFornecedor } from 'app/shared/model/cliente-fornecedor.model';

describe('Component Tests', () => {
  describe('ClienteFornecedor Management Detail Component', () => {
    let comp: ClienteFornecedorDetailComponent;
    let fixture: ComponentFixture<ClienteFornecedorDetailComponent>;
    const route = ({ data: of({ clienteFornecedor: new ClienteFornecedor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MmgestorTestModule],
        declarations: [ClienteFornecedorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClienteFornecedorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClienteFornecedorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load clienteFornecedor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.clienteFornecedor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
