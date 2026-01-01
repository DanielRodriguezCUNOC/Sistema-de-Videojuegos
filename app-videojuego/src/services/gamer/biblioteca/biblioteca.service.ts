import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { VideojuegoCompradoResponseDto } from '../../../models/dtos/gamer/biblioteca/videojuego-comprado-response-dto';
import { VideojuegoCompradoRequestDto } from '../../../models/dtos/gamer/biblioteca/videojuego-comprado-request-dto';

@Injectable({
  providedIn: 'root',
})
export class BibliotecaService {
  private apiUrl = `${environment.apiBaseUrl}/gamer/biblioteca`;

  constructor(private http: HttpClient) {}

  obtenerListadoVideojuegos(idUsuario: number) {
    console.log('Obteniendo listado de videojuegos para el usuario ID:', idUsuario);
    return this.http.get<VideojuegoCompradoResponseDto[]>(`${this.apiUrl}/listado/${idUsuario}`);
  }

  cambiarEstadoInstalacion(data: VideojuegoCompradoRequestDto) {
    return this.http.put(`${this.apiUrl}/cambiar-estado-instalacion`, data);
  }
}
