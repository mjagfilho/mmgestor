import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MmgestorSharedModule } from 'app/shared/shared.module';
import { AssociadosHarasComponent } from './associados-haras.component';
import { AssociadosHarasDetailComponent } from './associados-haras-detail.component';
import { AssociadosHarasUpdateComponent } from './associados-haras-update.component';
import { AssociadosHarasDeleteDialogComponent } from './associados-haras-delete-dialog.component';
import { associadosHarasRoute } from './associados-haras.route';

@NgModule({
  imports: [MmgestorSharedModule, RouterModule.forChild(associadosHarasRoute)],
  declarations: [
    AssociadosHarasComponent,
    AssociadosHarasDetailComponent,
    AssociadosHarasUpdateComponent,
    AssociadosHarasDeleteDialogComponent
  ],
  entryComponents: [AssociadosHarasDeleteDialogComponent]
})
export class MmgestorAssociadosHarasModule {}
