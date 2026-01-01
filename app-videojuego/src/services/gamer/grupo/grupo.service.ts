import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { GrupoRequestDto } from '../../../models/dtos/gamer/grupo/grupo-request-dto';

@Injectable({
  providedIn: 'root',
})
export class GrupoService {
  private apiUrl = `${environment.apiBaseUrl}/grupo`;
  constructor(private http: HttpClient) {}

  obtenerGruposPorUsuario(idUsuario: number) {
    return this.http.get(`${this.apiUrl}/listado/${idUsuario}`);
  }

  registrarGrupo(data: GrupoRequestDto) {
    return this.http.post(`${this.apiUrl}/crear`, data);
  }
}
