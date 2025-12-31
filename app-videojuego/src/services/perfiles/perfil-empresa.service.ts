import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { Observable } from 'rxjs';
import { PerfilVideojuegoResponseDTO } from '../../models/dtos/videojuego/perfil-videojuego-response-dto';

@Injectable({
  providedIn: 'root',
})
export class PerfilEmpresaService {
  private apiUrl = `${environment.apiBaseUrl}/perfil`;

  constructor(private http: HttpClient) {}

  obtenerPerfilEmpresa(nombreEmpresa: string): Observable<PerfilVideojuegoResponseDTO> {
    return this.http.get<PerfilVideojuegoResponseDTO>(`${this.apiUrl}/empresa/${nombreEmpresa}`);
  }
}
