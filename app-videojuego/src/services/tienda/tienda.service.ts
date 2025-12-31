import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { DatosVideojuegoTiendaDTO } from '../../models/dtos/videojuego/datos-videojuego-tienda-dto';

@Injectable({
  providedIn: 'root',
})
export class TiendaService {
  private apiUrl = `${environment.apiBaseUrl}/tienda`;

  constructor(private http: HttpClient) {}

  obtenerVideojuegos(offset: number, limit: number) {
    return this.http.get<DatosVideojuegoTiendaDTO[]>(
      `${this.apiUrl}/obtener-videojuegos/${offset}/${limit}`
    );
  }

  obtenerVideojuegoPorTitulo(titulo: string) {
    return this.http.get<DatosVideojuegoTiendaDTO>(
      `${this.apiUrl}/obtener-videojuegos-titulo/${titulo}`
    );
  }

  obtenerVideojuegosPorCategoria(categoriaId: number) {
    return this.http.get<DatosVideojuegoTiendaDTO[]>(
      `${this.apiUrl}/obtener-videojuegos-categoria/${categoriaId}`
    );
  }
}
