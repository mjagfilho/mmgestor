import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MmgestorSharedModule } from 'app/shared/shared.module';
import { AnimalComponent } from './animal.component';
import { AnimalDetailComponent } from './animal-detail.component';
import { AnimalUpdateComponent } from './animal-update.component';
import { AnimalDeleteDialogComponent } from './animal-delete-dialog.component';
import { animalRoute } from './animal.route';

@NgModule({
  imports: [MmgestorSharedModule, RouterModule.forChild(animalRoute)],
  declarations: [AnimalComponent, AnimalDetailComponent, AnimalUpdateComponent, AnimalDeleteDialogComponent],
  entryComponents: [AnimalDeleteDialogComponent],
})
export class MmgestorAnimalModule {}
