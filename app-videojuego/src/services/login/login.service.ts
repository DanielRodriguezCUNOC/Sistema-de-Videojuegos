import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { UserResponseLoginDTO } from '../../models/dtos/login/user-response-login';
import { HttpClient } from '@angular/common/http';

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
