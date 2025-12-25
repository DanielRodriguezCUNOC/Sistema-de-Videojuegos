import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { UsuarioAdministradorResponseDTO } from '../../models/dtos/usuario/response/usuario-administrador-response-dto';
import { UsuarioEmpresaResponseDTO } from '../../models/dtos/usuario/response/usuario-empresa-response-dto-';
import { UsuarioGamerResponseDTO } from '../../models/dtos/usuario/response/usuario-gamer-response-dto';

@Injectable({
  providedIn: 'root',
})
export class UsuarioResponseService {
  private apiUrl = `${environment.apiBaseUrl}/usuario/obtener`;
  constructor(private http: HttpClient) {}

  obtenerUsuarioAdminResponse(idUsuario: number | null | undefined) {
    if (!idUsuario) {
      throw new Error('El ID de usuario no puede ser nulo o indefinido.');
    }
    return this.http.get<UsuarioAdministradorResponseDTO>(`${this.apiUrl}/admin/${idUsuario}`);
  }

  obtenerUsuarioEmpresaResponse(idUsuario: number | null | undefined) {
    if (!idUsuario) {
      throw new Error('El ID de usuario no puede ser nulo o indefinido.');
    }
    return this.http.get<UsuarioEmpresaResponseDTO>(`${this.apiUrl}/empresa/${idUsuario}`);
  }

  obtenerUsuarioGamerResponse(idUsuario: number | null | undefined) {
    if (!idUsuario) {
      throw new Error('El ID de usuario no puede ser nulo o indefinido.');
    }
    return this.http.get<UsuarioGamerResponseDTO>(`${this.apiUrl}/gamer/${idUsuario}`);
  }
}
