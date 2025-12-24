import { Injectable } from '@angular/core';
import { CrearEmpresaDto } from '../../../models/dtos/administrador/empresa/crear-empresa-dto';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environment';

@Injectable({
  providedIn: 'root',
})
export class CrearEmpresaService {
  private apiURL = `${environment.apiBaseUrl}/empresa`;

  constructor(private http: HttpClient) {}

  crearEmpresa(datosEmpresa: CrearEmpresaDto): Observable<any> {
    const url = `${this.apiURL}/crear-empresa`;
    const formData = new FormData();

    // * Agregar todos los campos al FormData
    formData.append('nombreEmpresa', datosEmpresa.nombreEmpresa);
    formData.append('descripcion', datosEmpresa.descripcion);
    formData.append('estadoComentario', datosEmpresa.estadoComentario.toString());
    formData.append('correoUsuario', datosEmpresa.correoUsuario);
    formData.append('nombreCompleto', datosEmpresa.nombreCompleto);
    formData.append('password', datosEmpresa.password);
    formData.append('fechaNacimiento', this.formatDate(datosEmpresa.fechaNacimiento));
    formData.append('numeroTelefonico', datosEmpresa.numeroTelefonico);
    formData.append('pais', datosEmpresa.pais);

    // Agregar avatar solo si existe
    if (datosEmpresa.avatar) {
      formData.append('avatar', datosEmpresa.avatar);
    }

    return this.http.post(url, formData);
  }

  private formatDate(date: Date): string {
    if (!date) return '';

    const d = date instanceof Date ? date : new Date(date);

    // * Formatear YYYY-MM-DD
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }
}
