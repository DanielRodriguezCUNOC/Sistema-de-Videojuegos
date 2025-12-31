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

  obtenerVideojuegos(offset: number, limite: number) {
    return this.http.get<DatosVideojuegoTiendaDTO[]>(
      `${this.apiUrl}/videojuegos?offset=${offset}&limite=${limite}`
    );
  }

  obtenerVideojuegoPorNombre(nombre: string) {
    return this.http.get<DatosVideojuegoTiendaDTO>(
      `${this.apiUrl}/videojuegos/buscar?nombre=${nombre}`
    );
  }

  obtenerVideojuegosPorCategoria(categoriaId: number) {
    return this.http.get<DatosVideojuegoTiendaDTO[]>(
      `${this.apiUrl}/videojuegos/categoria/${categoriaId}`
    );
  }
}
