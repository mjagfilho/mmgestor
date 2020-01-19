import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MmGestorSharedModule } from 'app/shared/shared.module';
import { ClienteFornecedorComponent } from './cliente-fornecedor.component';
import { ClienteFornecedorDetailComponent } from './cliente-fornecedor-detail.component';
import { ClienteFornecedorUpdateComponent } from './cliente-fornecedor-update.component';
import { ClienteFornecedorDeleteDialogComponent } from './cliente-fornecedor-delete-dialog.component';
import { clienteFornecedorRoute } from './cliente-fornecedor.route';

@NgModule({
  imports: [MmGestorSharedModule, RouterModule.forChild(clienteFornecedorRoute)],
  declarations: [
    ClienteFornecedorComponent,
    ClienteFornecedorDetailComponent,
    ClienteFornecedorUpdateComponent,
    ClienteFornecedorDeleteDialogComponent
  ],
  entryComponents: [ClienteFornecedorDeleteDialogComponent]
})
export class MmGestorClienteFornecedorModule {}
