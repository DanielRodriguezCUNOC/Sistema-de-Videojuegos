import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { PerfilVideojuegoResponseDTO } from '../../models/dtos/videojuego/perfil-videojuego-response-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PerfilVideojuegoService {
  private apiUrl = `${environment.apiBaseUrl}/perfil/videojuego`;
  constructor(private http: HttpClient) {}

  obtenerPerfilVideojuego(videojuegoId: number): Observable<PerfilVideojuegoResponseDTO> {
    return this.http.get<PerfilVideojuegoResponseDTO>(
      `${this.apiUrl}/obtener-informacion/${videojuegoId}`
    );
  }
}
