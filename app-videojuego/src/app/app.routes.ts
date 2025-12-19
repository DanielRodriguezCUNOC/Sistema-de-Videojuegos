import { Routes } from '@angular/router';
import { HomePageComponent } from '../pages/home-page.component/home-page.component';
import { ModuloUsuarioPageComponent } from '../pages/usuario/modulo-usuario-page.component/modulo-usuario-page.component';
import { CrearUsuarioGamerPageComponent } from '../pages/usuario/crear/crear-usuario-gamer-page.component/crear-usuario-gamer-page.component';
import { CrearUsuarioGamerComponent } from '../components/usuario/crear/crear-usuario-gamer.component/crear-usuario-gamer.component';
import { GamerModuleComponent } from '../components/usuario-gamer/gamer-module.component/gamer-module.component';
import { EmpresaModuleComponent } from '../components/usuario-empresa/empresa-module.component/empresa-module.component';
import { AdminModuleComponent } from '../components/usuario-admin/admin-module.component/admin-module.component';

export const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
  },
  {
    path: 'crear-cuenta',
    component: CrearUsuarioGamerPageComponent,

    children: [
      {
        path: 'gamer-form',
        component: CrearUsuarioGamerComponent,
      },
      {
        path: '',
        redirectTo: 'gamer-form',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: 'user-gamer',
    component: ModuloUsuarioPageComponent,
    children: [
      {
        path: 'gamer-module',
        component: GamerModuleComponent,
      },
      {
        path: '',
        redirectTo: 'gamer-module',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: 'user-empresa',
    component: ModuloUsuarioPageComponent,
    children: [
      {
        path: 'empresa-module',
        component: EmpresaModuleComponent,
      },
      {
        path: '',
        redirectTo: 'empresa-module',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: 'user-admin',
    component: ModuloUsuarioPageComponent,
    children: [
      {
        path: 'admin-module',
        component: AdminModuleComponent,
      },
      {
        path: '',
        redirectTo: 'admin-module',
        pathMatch: 'full',
      },
    ],
  },
];
