// src/services/login/masterlogin.ts
import { Injectable } from '@angular/core';
import { UserResponseLoginDTO } from '../../models/dtos/login/user-response-login';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MasterLoginService {
  private isLoggedInSubject = new BehaviorSubject<boolean>(false);
  public isLoggedIn$: Observable<boolean> = this.isLoggedInSubject.asObservable();

  private currentUserSubject = new BehaviorSubject<UserResponseLoginDTO | null>(null);
  public currentUser$: Observable<UserResponseLoginDTO | null> =
    this.currentUserSubject.asObservable();

  private readonly STORAGE_KEY = 'usuarioActual';

  constructor() {
    this.checkLoginStatus();
  }

  private checkLoginStatus(): void {
    try {
      const userLoginData = localStorage.getItem(this.STORAGE_KEY);
      if (userLoginData) {
        const user = JSON.parse(userLoginData) as UserResponseLoginDTO;

        if (this.isValidUser(user)) {
          this.currentUserSubject.next(user);
          this.isLoggedInSubject.next(true);
        } else {
          console.warn('Datos de usuario inválidos en localStorage');
          this.clearInvalidData();
        }
      }
    } catch (error) {
      console.error('Error al verificar el estado de login:', error);
      this.clearInvalidData();
    }
  }

  private isValidUser(user: any): user is UserResponseLoginDTO {
    return user && user.idRol > 0 && user.correoUsuario;
  }

  private clearInvalidData(): void {
    try {
      localStorage.removeItem(this.STORAGE_KEY);
    } catch (error) {
      console.error('Error al limpiar datos inválidos:', error);
    }
    this.isLoggedInSubject.next(false);
    this.currentUserSubject.next(null);
  }

  setLogin(user: UserResponseLoginDTO): void {
    if (!this.isValidUser(user)) {
      throw new Error('Datos de usuario inválidos');
    }

    try {
      localStorage.setItem(this.STORAGE_KEY, JSON.stringify(user));
      this.isLoggedInSubject.next(true);
      this.currentUserSubject.next(user);
    } catch (error) {
      console.error('Error al guardar datos de usuario:', error);
      throw new Error('No se pudo guardar la sesión');
    }
  }

  setLogout(): void {
    try {
      localStorage.removeItem(this.STORAGE_KEY);
    } catch (error) {
      console.error('Error al eliminar los datos de localStorage:', error);
    }
    this.isLoggedInSubject.next(false);
    this.currentUserSubject.next(null);
  }

  getCurrentUser(): UserResponseLoginDTO | null {
    return this.currentUserSubject.value;
  }

  isLoggedIn(): boolean {
    return this.isLoggedInSubject.value;
  }

  /**
   * Convierte el avatar Base64 a una URL utilizable en src de img
   */
  /*getAvatarUrl(): string {
    const user = this.getCurrentUser();
    if (user?.avatar) {
      if (user.avatar.startsWith('data:image/')) {
        return user.avatar;
      }
    }
    return '';
  }*/

  /**
   * Obtiene el correo del usuario o un valor por defecto
   */
  getUserDisplayCorreo(): string {
    const user = this.getCurrentUser();
    if (user?.correoUsuario) {
      return `Usuario #${user.correoUsuario}`;
    }
    return 'Usuario';
  }

  /**
   * Obtiene el ID del usuario
   */
  getUserId(): number | null {
    const user = this.getCurrentUser();
    return user?.idUsuario || null;
  }

  /**
   * Verifica si el usuario tiene un rol específico
   */
  hasRole(roleId: number): boolean {
    const user = this.getCurrentUser();
    return user ? user.idRol === roleId : false;
  }

  /**
   * Obtiene el rol del usuario como número
   */
  getUserRole(): number | null {
    const user = this.getCurrentUser();
    return user ? user.idRol : null;
  }
}
