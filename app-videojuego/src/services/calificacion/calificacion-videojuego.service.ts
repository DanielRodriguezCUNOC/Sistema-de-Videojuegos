import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { CalificacionVideojuegoRequestDTO } from '../../models/dtos/videojuego/calificacion-videojuego-request-dto';

@Injectable({
  providedIn: 'root',
})
export class CalificacionVideojuegoService {
  private apiUrl = `${environment.apiBaseUrl}/calificacion-videojuego`;

  constructor(private http: HttpClient) {}

  registrarCalificacion(data: CalificacionVideojuegoRequestDTO) {
    return this.http.post(`${this.apiUrl}/registrar-calificacion`, data);
  }
}
