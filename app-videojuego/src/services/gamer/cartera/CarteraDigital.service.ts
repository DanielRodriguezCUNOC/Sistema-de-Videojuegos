import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { RecargarCarteraRequestDTO } from '../../../models/dtos/gamer/cartera/RecargarCarteraRequestDTO';

@Injectable({
  providedIn: 'root',
})
export class CarteraDigitalService {
  apiUrl = `${environment.apiBaseUrl}/gamer/cartera-digital`;

  constructor(private http: HttpClient) {}

  recargarCartera(data: RecargarCarteraRequestDTO) {
    if (data.idUsuario === null) {
      throw new Error('ID de usuario no puede ser null');
    }
    return this.http.post(`${this.apiUrl}/recargar-cartera`, data);
  }

  obtenerSaldo(idUsuario: number | null) {
    if (idUsuario === null) {
      throw new Error('ID de usuario no puede ser null');
    }
    return this.http.get<number>(`${this.apiUrl}/obtener-saldo/${idUsuario}`);
  }
}
