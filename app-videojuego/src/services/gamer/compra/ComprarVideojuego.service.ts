import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { ComprarVideojuegoRequestDTO } from '../../../models/dtos/gamer/compra/ComprarVideojuegoRequestDTO';

@Injectable({
  providedIn: 'root',
})
export class ComprarVideojuegoService {
  private apiUrl = `${environment.apiBaseUrl}/gamer/compra`;

  constructor(private http: HttpClient) {}

  comprarVideojuego(data: ComprarVideojuegoRequestDTO) {
    return this.http.post(`${this.apiUrl}/comprar-videojuego/`, data);
  }
}
