import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { FormatoFecha } from '../../../utils/formato-fecha';
import { VideojuegoRequestDto } from '../../../models/dtos/empresa/videojuego/videojuego-request-dto';
import { Observable } from 'rxjs';
import { MasterLoginService } from '../../login/masterlogin.service';
import { ListaVideojuegosDto } from '../../../models/dtos/empresa/videojuego/lista-videojuegos-dto';
import { EditarVideojuegoDto } from '../../../models/dtos/empresa/videojuego/editar-videojuego-dto';
import { EditarPortadaVideojuego } from '../../../models/dtos/empresa/videojuego/editar-portada-videojuego-dto';
import { editarEstadoVideojuegoDto } from '../../../models/dtos/empresa/videojuego/editar-estado-videojuego-dto';

@Injectable({
  providedIn: 'root',
})
export class CatalogoVideojuegosService {
  apiUrl = `${environment.apiBaseUrl}/empresa/gestion_videojuegos`;
  private formatearFecha = new FormatoFecha();

  constructor(private http: HttpClient, private masterLoginService: MasterLoginService) {}

  obtenerCatalogoVideojuegos() {
    return this.http.get<ListaVideojuegosDto>(`${this.apiUrl}/catalogo`);
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
    const url = `${this.apiUrl}/cambiar-visibilidad`;
    return this.http.put(url, data);
  }

  editarPortadaVideojuego(data: EditarPortadaVideojuego): Observable<any> {
    const url = `${this.apiUrl}/editar-portada/`;

    const formData = new FormData();
    formData.append('idVideojuego', data.idVideojuego);
    formData.append('nuevaPortada', data.nuevaPortada);

    return this.http.put(url, formData);
  }

  editarVideojuego(idVideojuego: string, datosVideojuego: EditarVideojuegoDto): Observable<any> {
    const url = `${this.apiUrl}/editar-videojuego/${idVideojuego}`;
    const formData = new FormData();

    formData.append('titulo', datosVideojuego.titulo);
    formData.append('descripcion', datosVideojuego.descripcion);

    formData.append('precio', datosVideojuego.precio.toString());
    formData.append('recursosMinimos', datosVideojuego.recurosMinimos);

    return this.http.put(url, formData);
  }
}
