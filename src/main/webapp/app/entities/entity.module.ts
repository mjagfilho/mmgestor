import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'endereco',
        loadChildren: () => import('./endereco/endereco.module').then(m => m.MmgestorEnderecoModule)
      },
      {
        path: 'haras',
        loadChildren: () => import('./haras/haras.module').then(m => m.MmgestorHarasModule)
      },
      {
        path: 'tipo-local',
        loadChildren: () => import('./tipo-local/tipo-local.module').then(m => m.MmgestorTipoLocalModule)
      },
      {
        path: 'local',
        loadChildren: () => import('./local/local.module').then(m => m.MmgestorLocalModule)
      },
      {
        path: 'cliente-fornecedor',
        loadChildren: () => import('./cliente-fornecedor/cliente-fornecedor.module').then(m => m.MmgestorClienteFornecedorModule)
      },
      {
        path: 'animal',
        loadChildren: () => import('./animal/animal.module').then(m => m.MmgestorAnimalModule)
      },
      {
        path: 'dados-associacao',
        loadChildren: () => import('./dados-associacao/dados-associacao.module').then(m => m.MmgestorDadosAssociacaoModule)
      },
      {
        path: 'associado',
        loadChildren: () => import('./associado/associado.module').then(m => m.MmgestorAssociadoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MmgestorEntityModule {}
