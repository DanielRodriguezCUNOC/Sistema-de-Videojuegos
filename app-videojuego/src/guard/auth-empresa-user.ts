import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authEmpresaUserGuard: CanActivateFn = () => {
  const router = inject(Router);
  // *obtiene el usuario actual del localStorage
  const usuarioActual = localStorage.getItem('usuarioActual');
  if (!usuarioActual) {
    return router.createUrlTree(['/login']);
  }
  const usuario = JSON.parse(usuarioActual);

  return usuario.idRol === 2 ? true : router.createUrlTree(['/access-denied']);
};
