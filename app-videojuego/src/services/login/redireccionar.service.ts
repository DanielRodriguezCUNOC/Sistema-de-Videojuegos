import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class RedireccionarService {
  private readonly RUTA_ROL = {
    1: '/user-admin/admin-module',
    2: '/user-empresa/empresa-module',
    3: '/user-gamer/gamer-module',
  } as const;

  private readonly RUTA_INICIO = '/login';

  constructor(private router: Router) {}

  redireccionarSegunRol(idRol: number): Promise<boolean> {
    const ruta = this.RUTA_ROL[idRol as keyof typeof this.RUTA_ROL] || this.RUTA_INICIO;
    return this.router.navigateByUrl(ruta);
  }

  getRouteByRole(roleId: number): string {
    return this.RUTA_ROL[roleId as keyof typeof this.RUTA_ROL] || this.RUTA_INICIO;
  }

  redirectToLogin(): Promise<boolean> {
    return this.router.navigateByUrl(this.RUTA_INICIO);
  }

  redirectToHome(): Promise<boolean> {
    return this.router.navigateByUrl('');
  }
}
