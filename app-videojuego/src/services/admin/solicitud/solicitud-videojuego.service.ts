import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ListaSolicitudVideojuegoDTO } from '../../../models/dtos/administrador/videojuego/lista-solicitud-videojuego-dto';
import { SolicitudVideojuegoRequestDTO } from '../../../models/dtos/administrador/videojuego/solicitud-videojuego-request-dto';

@Injectable({
  providedIn: 'root',
})
export class SolicitudVideojuegoService {
  private apiUrl = `${environment.apiBaseUrl}/admin/solicitud-videojuego`;
  constructor(private http: HttpClient) {}

  getSolicitudes(): Observable<ListaSolicitudVideojuegoDTO> {
    return this.http.get<ListaSolicitudVideojuegoDTO>(`${this.apiUrl}/listar-solicitudes`);
  }

  gestionarSolicitud(data: SolicitudVideojuegoRequestDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}/gestionar-solicitud`, data);
  }
}
