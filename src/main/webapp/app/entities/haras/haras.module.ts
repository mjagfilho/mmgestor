import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MmgestorSharedModule } from 'app/shared/shared.module';
import { HarasComponent } from './haras.component';
import { HarasDetailComponent } from './haras-detail.component';
import { HarasUpdateComponent } from './haras-update.component';
import { HarasDeleteDialogComponent } from './haras-delete-dialog.component';
import { harasRoute } from './haras.route';

@NgModule({
  imports: [MmgestorSharedModule, RouterModule.forChild(harasRoute)],
  declarations: [HarasComponent, HarasDetailComponent, HarasUpdateComponent, HarasDeleteDialogComponent],
  entryComponents: [HarasDeleteDialogComponent]
})
export class MmgestorHarasModule {}
