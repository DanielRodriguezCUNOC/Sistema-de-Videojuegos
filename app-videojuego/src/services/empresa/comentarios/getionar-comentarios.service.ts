import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { EditarEstadoEmpresaResponseDto } from '../../../models/dtos/empresa/comentario/editar-estado-empresa-response-dto';
import { EditarEstadoVideojuegoResponseDto } from '../../../models/dtos/empresa/comentario/editar-estado-videojuego-response-dto';
import { HttpClient } from '@angular/common/http';
import { EditarEstadoEmpresaRequestDto } from '../../../models/dtos/empresa/comentario/editar-estado-empresa-request-dto';
import { Observable } from 'rxjs';
import { EditarEstadoVideojuegoRequestDto } from '../../../models/dtos/empresa/comentario/editar-estado-videojuego-request-dto';

@Injectable({
  providedIn: 'root',
})
export class GestionarComentariosService {
  private apiUrl = `${environment.apiBaseUrl}/empresa/gestionar-comentarios`;

  constructor(private http: HttpClient) {}

  obtenerEstadoComentarioEmpresa(idUsuario: number): Observable<EditarEstadoEmpresaResponseDto> {
    return this.http.get<EditarEstadoEmpresaResponseDto>(
      `${this.apiUrl}/estado-comentarios-empresa/${idUsuario}`
    );
  }

  actualizarEstadoComentarioEmpresa(data: EditarEstadoEmpresaRequestDto) {
    return this.http.put(`${this.apiUrl}/actualizar-estado-empresa`, data);
  }

  obtenerEstadoComentarioVideojuego(
    idUsuario: number
  ): Observable<EditarEstadoVideojuegoResponseDto[]> {
    return this.http.get<EditarEstadoVideojuegoResponseDto[]>(
      `${this.apiUrl}/estado-comentarios-videojuego/${idUsuario}`
    );
  }

  actualizarEstadoComentarioVideojuego(data: EditarEstadoVideojuegoRequestDto) {
    return this.http.put(`${this.apiUrl}/actualizar-estado-videojuego`, data);
  }
}
