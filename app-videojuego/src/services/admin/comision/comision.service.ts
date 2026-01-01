import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ObtenerComisionGlobalDTO } from '../../../models/dtos/comision/obtener-comision-global-dto';
import { EditarComisionGlobalDto } from '../../../models/dtos/comision/editar-comision-global-dto';
import { ListaComisionEspecificaDTO } from '../../../models/dtos/comision/lista-comision-especifica-dto';
import { CrearComisionEspecificaDTO } from '../../../models/dtos/comision/crear-comision-especifica-dto';
import { EditarComisionEspecificaDTO } from '../../../models/dtos/comision/editar-comision-especifica-dto';
import { ComisionEspecificaDTO } from '../../../models/dtos/comision/comision-especifica-dto';
import { environment } from '../../../environment/environment';

@Injectable({
  providedIn: 'root',
})
export class ComisionService {
  constructor(private http: HttpClient) {}

  obtenerComisionGlobal(): Observable<ObtenerComisionGlobalDTO> {
    const apiUrl = `${environment.apiBaseUrl}/comision-global`;
    return this.http.get<ObtenerComisionGlobalDTO>(`${apiUrl}/obtener-comision`);
  }

  editarComisionGlobal(dto: EditarComisionGlobalDto): Observable<any> {
    const apiUrl = `${environment.apiBaseUrl}/comision-global`;
    return this.http.put<any>(`${apiUrl}/actualizar-comision`, dto);
  }

  obtenerComisionesEspecificas(): Observable<ListaComisionEspecificaDTO> {
    const apiUrl = `${environment.apiBaseUrl}/comision-especifica`;
    return this.http.get<ListaComisionEspecificaDTO>(`${apiUrl}/obtener-comisiones`);
  }

  obtenerComisionEspecificaPorId(id: number): Observable<ComisionEspecificaDTO> {
    const apiUrl = `${environment.apiBaseUrl}/comision-especifica`;
    return this.http.get<ComisionEspecificaDTO>(`${apiUrl}/obtener-comisiones/${id}`);
  }

  crearComisionEspecifica(dto: CrearComisionEspecificaDTO): Observable<any> {
    const apiUrl = `${environment.apiBaseUrl}/comision-especifica`;
    return this.http.post<any>(`${apiUrl}/registrar-comision`, dto);
  }

  editarComisionEspecifica(dto: EditarComisionEspecificaDTO): Observable<any> {
    const apiUrl = `${environment.apiBaseUrl}/comision-especifica`;
    return this.http.put<any>(`${apiUrl}/actualizar-comision`, dto);
  }

  eliminarComisionEspecifica(id: number): Observable<any> {
    const apiUrl = `${environment.apiBaseUrl}/comision-especifica`;
    return this.http.delete<any>(`${apiUrl}/comision-especifica/${id}`);
  }
}
