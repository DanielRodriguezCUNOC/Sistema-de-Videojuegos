import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { FormatoFecha } from '../../../utils/formato-fecha';
import { VideojuegoRequestDto } from '../../../models/dtos/empresa/videojuego/videojuego-request-dto';
import { Observable } from 'rxjs';
import { MasterLoginService } from '../../login/masterlogin.service';
import { ListaVideojuegosDto } from '../../../models/dtos/empresa/videojuego/lista-videojuegos-dto';
import { EditarPortadaVideojuego } from '../../../models/dtos/empresa/videojuego/editar-portada-videojuego-dto';
import { editarEstadoVideojuegoDto } from '../../../models/dtos/empresa/videojuego/editar-estado-videojuego-dto';
import { EditarVideojuegoRequestDto } from '../../../models/dtos/empresa/videojuego/editar-videojuego-request-dto';
import { EditarVideojuegoResponseDto } from '../../../models/dtos/empresa/videojuego/editar-videojuego-response-dto';

@Injectable({
  providedIn: 'root',
})
export class GestionVideojuegosService {
  apiUrl = `${environment.apiBaseUrl}/empresa/gestion_videojuegos`;
  private formatearFecha = new FormatoFecha();

  constructor(private http: HttpClient, private masterLoginService: MasterLoginService) {}

  obtenerCatalogoVideojuegos(idUsuario: number | null): Observable<ListaVideojuegosDto> {
    return this.http.get<ListaVideojuegosDto>(`${this.apiUrl}/catalogo/${idUsuario}`);
  }

  crearVideojuego(datosVideojuego: VideojuegoRequestDto): Observable<any> {
    const url = `${this.apiUrl}/crear-videojuego`;
    const formData = new FormData();

    formData.append('idUsuarioEmpresa', this.masterLoginService.getUserId()?.toString() || '');
    formData.append('titulo', datosVideojuego.titulo);
    formData.append('descripcion', datosVideojuego.descripcion);
    formData.append(
      'fechaLanzamiento',
      this.formatearFecha.formatDateToYYYYMMDD(datosVideojuego.fechaLanzamiento)
    );
    formData.append('precio', datosVideojuego.precio.toString());
    formData.append('recursosMinimos', datosVideojuego.recurosMinimos);
    if (datosVideojuego.imagen) {
      formData.append('imagen', datosVideojuego.imagen);
    }
    formData.append('clasificacion', datosVideojuego.clasificacion);

    datosVideojuego.categorias.forEach((categoria) => {
      formData.append('categorias', categoria);
    });

    return this.http.post(url, formData);
  }

  cambiarVisibilidadVideojuego(data: editarEstadoVideojuegoDto) {
    const url = `${this.apiUrl}/cambiar-estado`;
    return this.http.put(url, data);
  }

  editarPortadaVideojuego(data: EditarPortadaVideojuego): Observable<any> {
    const url = `${this.apiUrl}/editar-portada/`;

    const formData = new FormData();
    formData.append('idVideojuego', data.idVideojuego);
    formData.append('nuevaPortada', data.nuevaPortada);

    return this.http.put(url, formData);
  }

  editarVideojuego(datosVideojuego: EditarVideojuegoRequestDto): Observable<any> {
    const url = `${this.apiUrl}/editar-videojuego`;

    return this.http.put(url, datosVideojuego);
  }

  obtenerVideojuegoPorId(idVideojuego: string): Observable<EditarVideojuegoResponseDto> {
    const url = `${this.apiUrl}/obtener-videojuego/${idVideojuego}`;
    return this.http.get<EditarVideojuegoResponseDto>(url);
  }
}
