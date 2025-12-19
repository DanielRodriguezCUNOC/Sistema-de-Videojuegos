import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = () => {
  const router = inject(Router);

  const usuarioLoggedIn = localStorage.getItem('usuarioActual');

  return usuarioLoggedIn != null ? true : router.createUrlTree(['/login']);
};
