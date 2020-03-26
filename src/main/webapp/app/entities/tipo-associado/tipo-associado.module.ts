import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MmgestorSharedModule } from 'app/shared/shared.module';
import { TipoAssociadoComponent } from './tipo-associado.component';
import { TipoAssociadoDetailComponent } from './tipo-associado-detail.component';
import { TipoAssociadoUpdateComponent } from './tipo-associado-update.component';
import { TipoAssociadoDeleteDialogComponent } from './tipo-associado-delete-dialog.component';
import { tipoAssociadoRoute } from './tipo-associado.route';

@NgModule({
  imports: [MmgestorSharedModule, RouterModule.forChild(tipoAssociadoRoute)],
  declarations: [TipoAssociadoComponent, TipoAssociadoDetailComponent, TipoAssociadoUpdateComponent, TipoAssociadoDeleteDialogComponent],
  entryComponents: [TipoAssociadoDeleteDialogComponent]
})
export class MmgestorTipoAssociadoModule {}
