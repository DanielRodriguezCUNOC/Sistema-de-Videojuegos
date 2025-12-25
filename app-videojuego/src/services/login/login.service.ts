import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, catchError, throwError } from 'rxjs';
import { environment } from '../../environment/environment';
import { UserResponseLoginDTO } from '../../models/dtos/login/user-response-login';
import { MasterLoginService } from './masterlogin.service';
import { UserRequestLoginDTO } from '../../models/dtos/login/user-request-login';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  // TODO Declaramos la ruta hacia el endpoint de login
  private apiUrl = `${environment.apiBaseUrl}/login`;

  constructor(private http: HttpClient) {}

  //* Método para autenticar al usuario con el backend
  autenticacionBackend(usuario: FormData) {
    return this.http.post<UserResponseLoginDTO>(this.apiUrl, usuario);
  }
}
