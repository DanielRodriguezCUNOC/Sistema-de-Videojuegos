import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, catchError, throwError } from 'rxjs';
import { environment } from '../../environment/environment';
import { UserResponseLoginDTO } from '../../models/dtos/login/user-response-login';
import { MasterLoginService } from './masterlogin';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  // TODO Declaramos la ruta hacia el endpoint de login
  private apiUrl = `${environment.apiBaseUrl}/login`;

  // !Configuramos las opciones HTTP para las solicitudes
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' }),
  };

  // * Creamos un Subject para manejar el estado de autenticación
  private isLoggedInSubject = new BehaviorSubject<boolean>(false);

  // *Exponemos el estado de autenticación como un Observable
  private isLoggedIn$ = this.isLoggedInSubject.asObservable();

  constructor(private http: HttpClient, private masterLoginService: MasterLoginService) {
    this.estadoLogin();
  }

  // * Verificamos si el estado del login ha cambiado
  private estadoLogin(): void {
    const LOGGED = !!localStorage.getItem('usuarioActual');
    this.isLoggedInSubject.next(LOGGED);
  }

  //* Método para autenticar al usuario con el backend
  autenticacionBackend(correoUsuario: string, password: string) {
    //*Crear URLSearchParams para enviar como form data
    const body = new URLSearchParams();
    body.set('correoUsuario', correoUsuario);
    body.set('password', password);

    return this.http
      .post<UserResponseLoginDTO>(this.apiUrl, body.toString(), this.httpOptions)
      .pipe(
        map((user: UserResponseLoginDTO) => {
          //* Notificar al MasterLoginService sobre el inicio de sesión
          this.masterLoginService.setLogin(user);

          //* Retornamos los datos del usuario al componente
          return user;
        }),
        catchError((error) => {
          // ! No guardamos nada en localStorage
          return throwError(() => error);
        })
      );
  }

  // *Método para cerrar sesión
  logout(): void {
    this.masterLoginService.setLogout();
  }

  // *Getter para obtener el estado de autenticación
  get isLoggedIn() {
    return this.masterLoginService.isLoggedIn();
  }

  // *Getter para obtener el correoUsuario autenticado
  get currentUser() {
    return this.masterLoginService.getCurrentUser();
  }
}
