import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { EditarEstadoEmpresaResponseDto } from '../../../models/dtos/empresa/comentario/editar-estado-empresa-response-dto';
import { HttpClient } from '@angular/common/http';
import { EditarEstadoEmpresaRequestDto } from '../../../models/dtos/empresa/comentario/editar-estado-empresa-request-dto';
import { Observable } from 'rxjs';
import { EditarEstadoVideojuegoRequestDto } from '../../../models/dtos/empresa/comentario/editar-estado-videojuego-request';

@Injectable({
  providedIn: 'root',
})
export class GestionarComentariosService {
  private apiUrl = `${environment.apiBaseUrl}/empresa/gestionar-comentarios`;

  constructor(private http: HttpClient) {}

  obtenerEstadoComentarioEmpresa(idEmpresa: number): Observable<EditarEstadoEmpresaResponseDto> {
    return this.http.get<EditarEstadoEmpresaResponseDto>(
      `${this.apiUrl}/estado-comentarios-empresa/${idEmpresa}`
    );
  }

  actualizarEstadoComentarioEmpresa(data: EditarEstadoEmpresaRequestDto) {
    return this.http.put(`${this.apiUrl}/actualizar-estado-empresa`, data);
  }

  obtenerEstadoComentarioVideojuego(idEmpresa: number): Observable<EditarEstadoEmpresaResponseDto> {
    return this.http.get<EditarEstadoEmpresaResponseDto>(
      `${this.apiUrl}/estado-comentarios-videojuego/${idEmpresa}`
    );
  }

  actualizarEstadoComentarioVideojuego(data: EditarEstadoVideojuegoRequestDto) {
    return this.http.put(`${this.apiUrl}/actualizar-estado-videojuego`, data);
  }
}
