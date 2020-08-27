import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MmgestorSharedModule } from 'app/shared/shared.module';
import { TipoLocalComponent } from './tipo-local.component';
import { TipoLocalDetailComponent } from './tipo-local-detail.component';
import { TipoLocalUpdateComponent } from './tipo-local-update.component';
import { TipoLocalDeleteDialogComponent } from './tipo-local-delete-dialog.component';
import { tipoLocalRoute } from './tipo-local.route';

@NgModule({
  imports: [MmgestorSharedModule, RouterModule.forChild(tipoLocalRoute)],
  declarations: [TipoLocalComponent, TipoLocalDetailComponent, TipoLocalUpdateComponent, TipoLocalDeleteDialogComponent],
  entryComponents: [TipoLocalDeleteDialogComponent]
})
export class MmgestorTipoLocalModule {}
