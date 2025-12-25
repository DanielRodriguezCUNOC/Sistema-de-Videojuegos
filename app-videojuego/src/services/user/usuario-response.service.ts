import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { UsuarioAdministradorResponseDTO } from '../../models/dtos/usuario/response/usuario-administrador-response-dto';
import { UsuarioEmpresaResponseDTO } from '../../models/dtos/usuario/response/usuario-empresa-response-dto-';
import { UsuarioGamerResponseDto } from '../../models/dtos/usuario/response/usuario-gamer-response-dto';

@Injectable({
  providedIn: 'root',
})
export class UsuarioResponseService {
  private apiUrl = `${environment.apiBaseUrl}/usuario/obtener`;
  constructor(private http: HttpClient) {}

  obtenerUsuarioAdminResponse(idUsuario: number) {
    return this.http.get<UsuarioAdministradorResponseDTO>(`${this.apiUrl}/admin/${idUsuario}`);
  }

  obtenerUsuarioEmpresaResponse(idUsuario: number) {
    return this.http.get<UsuarioEmpresaResponseDTO>(`${this.apiUrl}/empresa/${idUsuario}`);
  }

  obtenerUsuarioGamerResponse(idUsuario: number) {
    return this.http.get<UsuarioGamerResponseDto>(`${this.apiUrl}/gamer/${idUsuario}`);
  }
}
