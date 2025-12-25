import { HttpClient } from '@angular/common/http';
import { Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environment/environment';
import { VideojuegoRequestDto } from '../../models/dtos/empresa/videojuego/videojuego-request-dto';
import { FormatoFecha } from '../../utils/formato-fecha';
import { MasterLoginService } from '../login/masterlogin.service';

@Inject({
  providedIn: 'root',
})
export class CrearVideojuegoService {
  apiURL = `${environment.apiBaseUrl}/videojuego`;
  private formatearFecha = new FormatoFecha();

  constructor(private http: HttpClient, private masterLoginService: MasterLoginService) {}

  crearVideojuego(datosVideojuego: VideojuegoRequestDto): Observable<any> {
    const url = `${this.apiURL}/crear-videojuego`;
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
}
