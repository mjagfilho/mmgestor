import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'usuario',
        loadChildren: () => import('./usuario/usuario.module').then(m => m.MmGestorUsuarioModule)
      },
      {
        path: 'endereco',
        loadChildren: () => import('./endereco/endereco.module').then(m => m.MmGestorEnderecoModule)
      },
      {
        path: 'responsavel',
        loadChildren: () => import('./responsavel/responsavel.module').then(m => m.MmGestorResponsavelModule)
      },
      {
        path: 'haras',
        loadChildren: () => import('./haras/haras.module').then(m => m.MmGestorHarasModule)
      },
      {
        path: 'tipo-local',
        loadChildren: () => import('./tipo-local/tipo-local.module').then(m => m.MmGestorTipoLocalModule)
      },
      {
        path: 'local',
        loadChildren: () => import('./local/local.module').then(m => m.MmGestorLocalModule)
      },
      {
        path: 'cliente-fornecedor',
        loadChildren: () => import('./cliente-fornecedor/cliente-fornecedor.module').then(m => m.MmGestorClienteFornecedorModule)
      },
      {
        path: 'animal',
        loadChildren: () => import('./animal/animal.module').then(m => m.MmGestorAnimalModule)
      },
      {
        path: 'dados-associacao',
        loadChildren: () => import('./dados-associacao/dados-associacao.module').then(m => m.MmGestorDadosAssociacaoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MmGestorEntityModule {}
