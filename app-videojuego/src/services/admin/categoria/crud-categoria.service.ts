import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CrearCategoriaDTO } from '../../../models/dtos/categoria/crear-categoria-dto';
import { EditarCategoriaDTO } from '../../../models/dtos/categoria/editar-categoria-dto';
import { ListaCategoriaDTO } from '../../../models/dtos/categoria/lista-categoria-dto';

@Injectable({
  providedIn: 'root',
})
export class CrudCategoriaService {
  //* LLamamos a la API para crear un usuario
  private apiURL = `${environment.apiBaseUrl}/categoria`;

  constructor(private http: HttpClient) {}

  crearCategoria(datosCategoria: CrearCategoriaDTO): Observable<any> {
    const url = `${this.apiURL}/crear-categoria`;
    return this.http.post(url, datosCategoria);
  }

  obtenerCategorias(): Observable<ListaCategoriaDTO> {
    const url = `${this.apiURL}/obtener-categorias`;
    return this.http.get<ListaCategoriaDTO>(url);
  }

  actualizarCategoria(editarCategoria: EditarCategoriaDTO): Observable<any> {
    const url = `${this.apiURL}/editar-categoria`;
    const formData = new FormData();
    formData.append('idCategoria', editarCategoria.idCategoria.toString());
    formData.append('categoria', editarCategoria.categoria);
    return this.http.put(url, formData);
  }

  eliminarCategoria(idCategoria: number): Observable<any> {
    const url = `${this.apiURL}/eliminar-categoria/${idCategoria}`;
    return this.http.delete(url);
  }
}
