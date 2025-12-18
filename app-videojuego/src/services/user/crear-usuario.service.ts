import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CrearUsuarioGamerDTO } from '../../models/dtos/usuario/crear/crear-usuario-gamer';
import { CrearUsuarioAdminDTO } from '../../models/dtos/usuario/crear/crear-usuario-admin';
import { CrearUsuarioEmpresaDTO } from '../../models/dtos/usuario/crear/crear-usuario-empresa';

@Injectable({
  providedIn: 'root',
})
export class CrearUsuarioService {
  //* LLamamos a la API para crear un usuario
  private apiURL = `${environment.apiBaseUrl}/usuario/crear-usuario-gamer`;

  constructor(private http: HttpClient) {}

  crearUsuarioGamer(datosUsuario: CrearUsuarioGamerDTO): Observable<any> {
    const formData = new FormData();
    formData.append('correoUsuario', datosUsuario.correoUsuario);
    formData.append('nickname', datosUsuario.nickname);
    formData.append('password', datosUsuario.password);
    formData.append('fechaNacimiento', datosUsuario.fechaNacimiento.toISOString());
    formData.append('numeroTelefonico', datosUsuario.numeroTelefonico);
    formData.append('pais', datosUsuario.pais);
    if (datosUsuario.avatar) {
      formData.append('avatar', datosUsuario.avatar);
    }

    return this.http.post(this.apiURL, formData);
  }

  crearUsuarioAdmin(datosUsuario: CrearUsuarioAdminDTO): Observable<any> {
    const formData = new FormData();
    formData.append('correoUsuario', datosUsuario.correoUsuario);
    formData.append('nombreCompleto', datosUsuario.nombreCompleto);
    formData.append('password', datosUsuario.password);
    formData.append('fechaNacimiento', datosUsuario.fechaNacimiento.toISOString());
    formData.append('numeroTelefonico', datosUsuario.numeroTelefonico);
    formData.append('pais', datosUsuario.pais);
    if (datosUsuario.avatar) {
      formData.append('avatar', datosUsuario.avatar);
    }

    return this.http.post(this.apiURL, formData);
  }

  crearUsuarioEmpresa(datosUsuario: CrearUsuarioEmpresaDTO): Observable<any> {
    const formData = new FormData();
    formData.append('correoUsuario', datosUsuario.correoUsuario);
    formData.append('nombreCompleto', datosUsuario.nombreCompleto);
    formData.append('password', datosUsuario.password);
    formData.append('fechaNacimiento', datosUsuario.fechaNacimiento.toISOString());
    formData.append('numeroTelefonico', datosUsuario.numeroTelefonico);
    formData.append('pais', datosUsuario.pais);
    if (datosUsuario.avatar) {
      formData.append('avatar', datosUsuario.avatar);
    }

    return this.http.post(this.apiURL, formData);
  }
}
