import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MmgestorSharedModule } from 'app/shared/shared.module';
import { DadosAssociacaoComponent } from './dados-associacao.component';
import { DadosAssociacaoDetailComponent } from './dados-associacao-detail.component';
import { DadosAssociacaoUpdateComponent } from './dados-associacao-update.component';
import { DadosAssociacaoDeleteDialogComponent } from './dados-associacao-delete-dialog.component';
import { dadosAssociacaoRoute } from './dados-associacao.route';

@NgModule({
  imports: [MmgestorSharedModule, RouterModule.forChild(dadosAssociacaoRoute)],
  declarations: [
    DadosAssociacaoComponent,
    DadosAssociacaoDetailComponent,
    DadosAssociacaoUpdateComponent,
    DadosAssociacaoDeleteDialogComponent
  ],
  entryComponents: [DadosAssociacaoDeleteDialogComponent]
})
export class MmgestorDadosAssociacaoModule {}
