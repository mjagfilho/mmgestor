import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MmGestorSharedModule } from 'app/shared/shared.module';
import { LocalComponent } from './local.component';
import { LocalDetailComponent } from './local-detail.component';
import { LocalUpdateComponent } from './local-update.component';
import { LocalDeleteDialogComponent } from './local-delete-dialog.component';
import { localRoute } from './local.route';

@NgModule({
  imports: [MmGestorSharedModule, RouterModule.forChild(localRoute)],
  declarations: [LocalComponent, LocalDetailComponent, LocalUpdateComponent, LocalDeleteDialogComponent],
  entryComponents: [LocalDeleteDialogComponent]
})
export class MmGestorLocalModule {}
