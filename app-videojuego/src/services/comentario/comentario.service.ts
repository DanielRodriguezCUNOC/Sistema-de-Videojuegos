import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { ComentarioRequestDTO } from '../../models/dtos/videojuego/comentarios/ComentarioRequestDTO';
import { Observable } from 'rxjs';
import { ComentarioResponseDTO } from '../../models/dtos/videojuego/comentarios/ComentarioResponseDTO';

@Injectable({
  providedIn: 'root',
})
export class ComentarioService {
  private apiUrl = `${environment.apiBaseUrl}/comentarios`;
  constructor(private http: HttpClient) {}

  obtenerComentariosPorVideojuego(idVideojuego: number): Observable<ComentarioResponseDTO[]> {
    return this.http.get<ComentarioResponseDTO[]>(
      `${this.apiUrl}/obtener-comentarios/${idVideojuego}`
    );
  }

  agregarComentario(data: ComentarioRequestDTO) {
    return this.http.post(`${this.apiUrl}/agregar-comentario/`, data);
  }
}
