import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GrupoResponseDto } from '../../../models/dtos/gamer/grupo/grupo-reponse-dto';
import { EliminarIntegranteDto } from '../../../models/dtos/gamer/grupo/eliminar-integrante-dto';
import { NuevoIntegranteRequestDto } from '../../../models/dtos/gamer/grupo/nuevo-integrante-request-dto';

@Injectable({
  providedIn: 'root',
})
export class IntegranteGrupoService {
  private apiUrl = `${environment.apiBaseUrl}/integrante-grupo`;

  constructor(private http: HttpClient) {}

  obtenerGrupos(idUsuario: number): Observable<GrupoResponseDto[]> {
    return this.http.get<GrupoResponseDto[]>(`${this.apiUrl}/obtener-grupos/${idUsuario}`);
  }

  eliminarIntegrante(data: EliminarIntegranteDto) {
    return this.http.delete(`${this.apiUrl}/eliminar-integrante`, { body: data });
  }

  agregarIntegrante(data: NuevoIntegranteRequestDto) {
    return this.http.post(`${this.apiUrl}/agregar-integrante`, data);
  }
}
