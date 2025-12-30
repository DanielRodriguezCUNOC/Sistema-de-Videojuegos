import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SolicitudVideojuegoRequestDTO } from '../../../models/dtos/administrador/videojuego/solicitud-videojuego-request-dto';
import { SolicitudVideojuegoResponseDTO } from '../../../models/dtos/administrador/videojuego/solicitud-videojuego-response-dto';

@Injectable({
  providedIn: 'root',
})
export class SolicitudVideojuegoService {
  private apiUrl = `${environment.apiBaseUrl}/admin/solicitud-videojuego`;
  constructor(private http: HttpClient) {}

  obtenerSolicitudes(): Observable<SolicitudVideojuegoResponseDTO[]> {
    return this.http.get<SolicitudVideojuegoResponseDTO[]>(`${this.apiUrl}/listar-solicitudes`);
  }

  gestionarSolicitud(data: SolicitudVideojuegoRequestDTO): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/gestionar-solicitud`, data);
  }
}
