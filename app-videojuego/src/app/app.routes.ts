import { Routes } from '@angular/router';
import { HomePageComponent } from '../pages/home-page.component/home-page.component';
import { ModuloUsuarioComponent } from '../components/usuario/modulo-usuario.component/modulo-usuario.component';
import { ModuloUsuarioPageComponent } from '../pages/usuario/modulo-usuario-page.component/modulo-usuario-page.component';
import { CrearUsuarioGamerPageComponent } from '../pages/usuario/crear/crear-usuario-gamer-page.component/crear-usuario-gamer-page.component';
import { CrearUsuarioGamerComponent } from '../components/usuario/crear/crear-usuario-gamer.component/crear-usuario-gamer.component';

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
        path: 'form',
        component: CrearUsuarioGamerComponent,
      },
      {
        path: '',
        redirectTo: 'form',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: 'user',
    component: ModuloUsuarioPageComponent,
    children: [
      {
        path: 'gamer-module',
        component: ModuloUsuarioComponent,
      },
      {
        path: '',
        redirectTo: 'gamer-module',
        pathMatch: 'full',
      },
    ],
  },
];
