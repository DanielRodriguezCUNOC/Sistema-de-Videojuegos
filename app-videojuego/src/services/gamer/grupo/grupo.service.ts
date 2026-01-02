import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { GrupoRequestDto } from '../../../models/dtos/gamer/grupo/grupo-request-dto';
import { EditarGrupoRequestDto } from '../../../models/dtos/gamer/grupo/editar-grupo-request-dto';
import { EditarEstadoGrupoDto } from '../../../models/dtos/gamer/grupo/editar-estado-grupo-dto';

@Injectable({
  providedIn: 'root',
})
export class GrupoService {
  private apiUrl = `${environment.apiBaseUrl}/grupo`;
  constructor(private http: HttpClient) {}

  registrarGrupo(data: GrupoRequestDto) {
    return this.http.post(`${this.apiUrl}/registrar-grupo`, data);
  }

  editarGrupo(data: EditarGrupoRequestDto) {
    return this.http.put(`${this.apiUrl}/editar-grupo`, data);
  }

  cambiarEstadoGrupo(data: EditarEstadoGrupoDto) {
    return this.http.put(`${this.apiUrl}/cambiar-estado-grupo`, data);
  }
}
