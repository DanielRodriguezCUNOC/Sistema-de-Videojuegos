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
  private apiUrl = `${environment.apiBaseUrl}/comision-global`;

  constructor(private http: HttpClient) {}

  obtenerComisionGlobal(): Observable<ObtenerComisionGlobalDTO> {
    return this.http.get<ObtenerComisionGlobalDTO>(`${this.apiUrl}/obtener-comision`);
  }

  editarComisionGlobal(dto: EditarComisionGlobalDto): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/actualizar-comision`, dto);
  }

  obtenerComisionesEspecificas(): Observable<ListaComisionEspecificaDTO> {
    return this.http.get<ListaComisionEspecificaDTO>(`${this.apiUrl}/comisiones-especificas`);
  }

  obtenerComisionEspecificaPorId(id: number): Observable<ComisionEspecificaDTO> {
    return this.http.get<ComisionEspecificaDTO>(`${this.apiUrl}/comision-especifica/${id}`);
  }

  crearComisionEspecifica(dto: CrearComisionEspecificaDTO): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/comision-especifica`, dto);
  }

  editarComisionEspecifica(dto: EditarComisionEspecificaDTO): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/comision-especifica`, dto);
  }

  eliminarComisionEspecifica(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/comision-especifica/${id}`);
  }
}
