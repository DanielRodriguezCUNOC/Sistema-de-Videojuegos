import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PerfilVideojuegoResponseDTO } from '../../models/dtos/videojuego/perfil-videojuego-response-dto';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root',
})
export class PerfilGamerService {
  private apiUrl = `${environment.apiBaseUrl}/perfil`;

  constructor(private http: HttpClient) {}

  obtenerPerfilGamer(nickname: string): Observable<PerfilVideojuegoResponseDTO> {
    return this.http.get<PerfilVideojuegoResponseDTO>(`${this.apiUrl}/usuario/${nickname}`);
  }
}
