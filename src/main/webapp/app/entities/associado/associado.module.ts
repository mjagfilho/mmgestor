import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MmgestorSharedModule } from 'app/shared/shared.module';
import { AssociadoComponent } from './associado.component';
import { AssociadoDetailComponent } from './associado-detail.component';
import { AssociadoUpdateComponent } from './associado-update.component';
import { AssociadoDeleteDialogComponent } from './associado-delete-dialog.component';
import { associadoRoute } from './associado.route';

@NgModule({
  imports: [MmgestorSharedModule, RouterModule.forChild(associadoRoute)],
  declarations: [AssociadoComponent, AssociadoDetailComponent, AssociadoUpdateComponent, AssociadoDeleteDialogComponent],
  entryComponents: [AssociadoDeleteDialogComponent]
})
export class MmgestorAssociadoModule {}
