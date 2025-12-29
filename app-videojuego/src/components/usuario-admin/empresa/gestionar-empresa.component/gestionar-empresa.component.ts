import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { ObtenerDatosEmpresaDTO } from '../../../../models/dtos/administrador/empresa/obtener-datos-empresa-dto';
import { ListaEmpresasDTO } from '../../../../models/dtos/administrador/empresa/lista-empresas-dto';
import { GestionEmpresaService } from '../../../../services/admin/empresa/gestion-empresa.service';
import { CambiarEstadoEmpresaDTO } from '../../../../models/dtos/administrador/empresa/cambiar-estado-empresa-dto';

@Component({
  selector: 'app-gestionar-empresa.component',
  imports: [SharePopupComponent],
  templateUrl: './gestionar-empresa.component.html',
  styleUrl: './gestionar-empresa.component.scss',
})
export class GestionarEmpresaComponent implements OnInit {
  empresas: ObtenerDatosEmpresaDTO[] = [];
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(private router: Router, private gestionEmpresaService: GestionEmpresaService) {}

  ngOnInit(): void {
    this.cargarEmpresas();
  }

  cargarEmpresas(): void {
    this.gestionEmpresaService.obtenerEmpresas().subscribe({
      next: (data: ListaEmpresasDTO) => {
        this.empresas = data.empresas;
      },
      error: (error) => {
        this.mostrarPopup('Error al cargar las empresas. Por favor, intente nuevamente.', 'error');
        console.error('Error al obtener empresas:', error);
      },
    });
  }

  regresar(): void {
    this.router.navigate(['/user-admin/admin-module']);
  }

  crearEmpresa(): void {
    this.router.navigate(['/user-admin/crear-empresa']);
  }

  editarEmpresa(idEmpresa: number, nombreEmpresa: string, descripcionEmpresa: string): void {
    this.router.navigate([
      '/user-admin/editar-empresa',
      idEmpresa,
      nombreEmpresa,
      descripcionEmpresa,
    ]);
  }

  cambiarEstadoEmpresa(idEmpresa: number, estadoActual: string): void {
    const data: CambiarEstadoEmpresaDTO = {
      idEmpresa: idEmpresa,
      estado: estadoActual === 'ACTIVA' ? 'INACTIVA' : 'ACTIVA',
    };
    this.gestionEmpresaService.cambiarEstadoEmpresa(data).subscribe({
      next: () => {
        this.mostrarPopup('Estado de la empresa actualizado correctamente.', 'success');
        this.cargarEmpresas();
      },
      error: (error) => {
        this.mostrarPopup(
          'Error al cambiar el estado de la empresa. Por favor, intente nuevamente.',
          'error'
        );
        console.error('Error al cambiar estado de la empresa:', error);
      },
    });
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
