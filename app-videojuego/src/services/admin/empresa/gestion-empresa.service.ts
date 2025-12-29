import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { CambiarEstadoEmpresaDTO } from '../../../models/dtos/administrador/empresa/cambiar-estado-empresa-dto';
import { HttpClient } from '@angular/common/http';
import { ListaEmpresasDTO } from '../../../models/dtos/administrador/empresa/lista-empresas-dto';
import { EditarEmpresaRequestDTO } from '../../../models/dtos/administrador/empresa/editar-empresa-request-dto';

@Injectable({
  providedIn: 'root',
})
export class GestionEmpresaService {
  private apiUrl = `${environment.apiBaseUrl}/admin/gestion-empresa`;
  constructor(private http: HttpClient) {}

  editarEmpresa(data: EditarEmpresaRequestDTO) {
    const url = `${this.apiUrl}/editar-empresa`;
    return this.http.put(url, data);
  }

  cambiarEstadoEmpresa(data: CambiarEstadoEmpresaDTO) {
    const url = `${this.apiUrl}/cambiar-estado`;
    return this.http.put(url, data);
  }

  obtenerEmpresas() {
    const url = `${this.apiUrl}/obtener-empresas`;
    return this.http.get<ListaEmpresasDTO>(url);
  }
}
