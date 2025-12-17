import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CrearUsuarioDTO } from '../../models/dtos/usuario/crear-usuario-dto';

@Injectable({
  providedIn: 'root',
})
export class CrearUsuarioService {
  //* LLamamos a la API para crear un usuario
  private apiURL = `${environment.apiBaseUrl}/usuario/crear`;

  constructor(private http: HttpClient) {}

  crearUsuario(datosUsuario: CrearUsuarioDTO): Observable<any> {
    const formData = new FormData();
    formData.append('correo_usuario', datosUsuario.correo_usuario);
    formData.append('nickname', datosUsuario.nickname);
    formData.append('password', datosUsuario.password);
    formData.append('fecha_nacimiento', datosUsuario.fecha_nacimiento.toISOString());
    formData.append('numero_telefonico', datosUsuario.numero_telefonico);
    formData.append('pais', datosUsuario.pais);
    if (datosUsuario.avatar) {
      formData.append('avatar', datosUsuario.avatar);
    }

    return this.http.post(this.apiURL, formData);
  }
}
