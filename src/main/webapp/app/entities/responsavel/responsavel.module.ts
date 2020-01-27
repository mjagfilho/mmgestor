import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MmgestorSharedModule } from 'app/shared/shared.module';
import { ResponsavelComponent } from './responsavel.component';
import { ResponsavelDetailComponent } from './responsavel-detail.component';
import { ResponsavelUpdateComponent } from './responsavel-update.component';
import { ResponsavelDeleteDialogComponent } from './responsavel-delete-dialog.component';
import { responsavelRoute } from './responsavel.route';

@NgModule({
  imports: [MmgestorSharedModule, RouterModule.forChild(responsavelRoute)],
  declarations: [ResponsavelComponent, ResponsavelDetailComponent, ResponsavelUpdateComponent, ResponsavelDeleteDialogComponent],
  entryComponents: [ResponsavelDeleteDialogComponent]
})
export class MmgestorResponsavelModule {}
