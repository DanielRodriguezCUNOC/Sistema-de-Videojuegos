import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authAdminUserGuard: CanActivateFn = () => {
  const router = inject(Router);

  // *obtiene el usuario actual del localStorage
  const usuarioActual = localStorage.getItem('usuarioActual');
  if (!usuarioActual) {
    return router.createUrlTree(['/login']);
  }
  const usuario = JSON.parse(usuarioActual);
  /*
   *verifica si el rol del usuario es admin (idRol === 1)
   *si es así, permite el acceso; de lo contrario, redirige a la página de inicio
   */
  return usuario.idRol === 1 ? true : router.createUrlTree(['/']);
};
