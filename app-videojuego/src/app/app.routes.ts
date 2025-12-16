import { Routes } from '@angular/router';
import { HomePageComponent } from '../pages/home-page.component/home-page.component';
import { ModuloUsuarioComponent } from '../components/usuario/modulo-usuario.component/modulo-usuario.component';
import { ModuloUsuarioPageComponent } from '../pages/modulo-usuario-page.component/modulo-usuario-page.component';

export const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
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
