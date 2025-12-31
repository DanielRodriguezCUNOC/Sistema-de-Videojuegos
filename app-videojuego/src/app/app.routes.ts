import { Routes } from '@angular/router';
import { HomePageComponent } from '../pages/home-page.component/home-page.component';
import { ModuloUsuarioPageComponent } from '../pages/usuario/modulo-usuario-page.component/modulo-usuario-page.component';
import { CrearUsuarioGamerPageComponent } from '../pages/usuario/crear/crear-usuario-gamer-page.component/crear-usuario-gamer-page.component';
import { CrearUsuarioGamerComponent } from '../components/usuario/crear/crear-usuario-gamer.component/crear-usuario-gamer.component';
import { GamerModuleComponent } from '../components/usuario-gamer/gamer-module.component/gamer-module.component';
import { EmpresaModuleComponent } from '../components/usuario-empresa/empresa-module.component/empresa-module.component';
import { AdminModuleComponent } from '../components/usuario-admin/admin-module.component/admin-module.component';
import { LoginPageComponent } from '../pages/login/login-page.component/login-page.component';
import { LoginComponent } from '../components/login/login.component/login.component';
import { CrearUsuarioAdminComponent } from '../components/usuario/crear/crear-usuario-admin.component/crear-usuario-admin.component';
import { GestionarCategoriaComponent } from '../components/usuario-admin/categoria/gestionar-categoria.component/gestionar-categoria.component';
import { CrearUsuarioEmpresaComponent } from '../components/usuario/crear/crear-usuario-empresa.component/crear-usuario-empresa.component';
import { GestionarComisionComponent } from '../components/usuario-admin/comision/gestionar-comision.component/gestionar-comision.component';
import { CrearEmpresaComponent } from '../components/usuario-admin/empresa/crear-empresa.component/crear-empresa.component';
import { GestionarComisionGlobalComponent } from '../components/usuario-admin/comision/gestionar-comision-global.component/gestionar-comision-global.component';
import { CrearComisionComponent } from '../components/usuario-admin/comision/crear-comision.component/crear-comision.component';
import { CrearComisionEspecificaComponent } from '../components/usuario-admin/comision/crear-comision-especifica.component/crear-comision-especifica.component';
import { EditarComisionComponent } from '../components/usuario-admin/comision/editar-comision.component/editar-comision.component';
import { EditarComisionEspecificaComponent } from '../components/usuario-admin/comision/editar-comision-especifica.component/editar-comision-especifica.component';
import { CrearVideojuegoComponent } from '../components/usuario-empresa/crear/crear-videojuego.component/crear-videojuego.component';
import { CrearCategoriaComponent } from '../components/usuario-admin/categoria/crear-categoria.component/crear-categoria.component';
import { GestionarComisionEspecificaComponent } from '../components/usuario-admin/comision/gestionar-comision-especifica.component/gestionar-comision-especifica.component';
import { GetionarCatalogoVideojuegosComponent } from '../components/usuario-empresa/gestion/getionar-catalogo-videojuegos.component/getionar-catalogo-videojuegos.component';
import { GetionarComentariosComponent } from '../components/usuario-empresa/gestion/getionar-comentarios.component/getionar-comentarios.component';
import { GestionarEmpresaComponent } from '../components/usuario-admin/empresa/gestionar-empresa.component/gestionar-empresa.component';
import { EditarEmpresaComponent } from '../components/usuario-admin/empresa/editar-empresa.component/editar-empresa.component';
import { EditarCategoriaComponent } from '../components/usuario-admin/categoria/editar-categoria.component/editar-categoria.component';
import { EditarPortadaComponent } from '../components/usuario-empresa/gestion/editar-portada.component/editar-portada.component';
import { EditarVideojuegoComponent } from '../components/usuario-empresa/gestion/editar-videojuego.component/editar-videojuego.component';
import { SolicitudVideojuegoComponent } from '../components/usuario-admin/videojuego/solicitud-videojuego.component/solicitud-videojuego.component';
import { CarteraDigitalComponent } from '../components/usuario-gamer/cartera/cartera-digital.component/cartera-digital.component';
import { RecargarCarteraDigitalComponent } from '../components/usuario-gamer/cartera/recargar-cartera-digital.component/recargar-cartera-digital.component';
import { ModuloTiendaComponent } from '../components/tienda/modulo-tienda.component/modulo-tienda.component';

export const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
  },

  {
    path: 'login',
    component: LoginPageComponent,
    children: [
      {
        path: 'form-login',
        component: LoginComponent,
      },
      {
        path: '',
        redirectTo: 'form-login',
        pathMatch: 'full',
      },
    ],
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
      {
        path: 'cartera-digital',
        component: CarteraDigitalComponent,
      },
      {
        path: 'recargar-cartera/:saldoActual',
        component: RecargarCarteraDigitalComponent,
      },
      {
        path: 'tienda',
        component: ModuloTiendaComponent,
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
      {
        path: 'registrar-usuario-empresa',
        component: CrearUsuarioEmpresaComponent,
      },
      {
        path: 'crear-videojuego',
        component: CrearVideojuegoComponent,
      },
      {
        path: 'gestionar-catalogo',
        component: GetionarCatalogoVideojuegosComponent,
      },
      {
        path: 'editar-videojuego/:idVideojuego',
        component: EditarVideojuegoComponent,
      },
      {
        path: 'editar-portada/:idVideojuego',
        component: EditarPortadaComponent,
      },
      {
        path: 'gestionar-comentarios',
        component: GetionarComentariosComponent,
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
      {
        path: 'crear-administrador',
        component: CrearUsuarioAdminComponent,
      },

      {
        path: 'gestionar-categorias',
        component: GestionarCategoriaComponent,
      },
      {
        path: 'crear-categoria',
        component: CrearCategoriaComponent,
      },
      {
        path: 'editar-categoria/:id/:categoria',
        component: EditarCategoriaComponent,
      },
      {
        path: 'gestionar-comisiones',
        component: GestionarComisionComponent,
      },
      {
        path: 'crear-empresa',
        component: CrearEmpresaComponent,
      },
      {
        path: 'gestionar-empresas',
        component: GestionarEmpresaComponent,
      },
      {
        path: 'editar-empresa/:idEmpresa/:nombreEmpresa/:descripcionEmpresa',
        component: EditarEmpresaComponent,
      },
      {
        path: 'comision-global',
        component: GestionarComisionGlobalComponent,
      },
      {
        path: 'comision-especifica',
        component: GestionarComisionEspecificaComponent,
      },
      {
        path: 'crear-comision-global',
        component: CrearComisionComponent,
      },
      {
        path: 'crear-comision-especifica',
        component: CrearComisionEspecificaComponent,
      },
      {
        path: 'editar-comision-global/:comisionGlobal/:comisionId',
        component: EditarComisionComponent,
      },
      {
        path: 'editar-comision-especifica/:idEmpresa/:nombreEmpresa/:comisionActual',
        component: EditarComisionEspecificaComponent,
      },
      {
        path: 'moderar-videojuegos',
        component: SolicitudVideojuegoComponent,
      },
    ],
  },
];
