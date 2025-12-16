import { Routes } from '@angular/router';
import { HomePageComponent } from '../pages/home-page.component/home-page.component';
import { ModuloUsuarioComponent } from '../components/usuario/modulo-usuario.component/modulo-usuario.component';
import { ModuloUsuarioPageComponent } from '../pages/usuario/modulo-usuario-page.component/modulo-usuario-page.component';
import { CrearUsuarioComponent } from '../components/usuario/crear-usuario.component/crear-usuario.component';
import { CrearUsuarioPageComponent } from '../pages/usuario/crear-usuario-page.component/crear-usuario-page.component';

export const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
  },
  {
    path: 'crear-cuenta',
    component: CrearUsuarioPageComponent,

    children: [
      {
        path: 'form',
        component: CrearUsuarioComponent,
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
