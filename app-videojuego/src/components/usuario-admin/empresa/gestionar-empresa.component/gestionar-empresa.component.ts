import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { ObtenerDatosEmpresaDTO } from '../../../../models/dtos/administrador/empresa/obtener-datos-empresa-dto';
import { ListaEmpresasDTO } from '../../../../models/dtos/administrador/empresa/lista-empresas-dto';

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

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.cargarEmpresas();
  }

  cargarEmpresas(): void {
    // TODO: Implementar servicio para cargar empresas del backend
    // Por ahora datos de ejemplo basados en el DTO
    this.empresas = [
      {
        idEmpresa: 1,
        nombreEmpresa: 'GameStudio Pro',
        descripcionEmpresa: 'Desarrolladora de videojuegos indie especializados en aventuras',
        estado: 'ACTIVO',
      },
      {
        idEmpresa: 2,
        nombreEmpresa: 'Digital Entertainment',
        descripcionEmpresa: 'Compañía de entretenimiento digital y desarrollo de juegos móviles',
        estado: 'INACTIVO',
      },
      {
        idEmpresa: 3,
        nombreEmpresa: 'Pixel Arts Games',
        descripcionEmpresa: 'Estudio creativo enfocado en juegos pixel art y retro gaming',
        estado: 'ACTIVO',
      },
    ];
  }

  regresar(): void {
    this.router.navigate(['/user-admin/admin-module']);
  }

  crearEmpresa(): void {
    this.router.navigate(['/user-admin/crear-empresa']);
  }

  editarEmpresa(idEmpresa: number): void {
    // TODO: Implementar ruta para editar empresa
    this.mostrarPopup(`Editar empresa con ID: ${idEmpresa}`, 'info');
  }

  cambiarEstadoEmpresa(idEmpresa: number, estadoActual: string): void {
    // TODO: Implementar servicio para cambiar estado
    const empresa = this.empresas.find((e) => e.idEmpresa === idEmpresa);
    if (empresa) {
      const nuevoEstado = estadoActual === 'ACTIVO' ? 'INACTIVO' : 'ACTIVO';
      empresa.estado = nuevoEstado;

      const accion = nuevoEstado === 'ACTIVO' ? 'activada' : 'desactivada';
      this.mostrarPopup(`Empresa "${empresa.nombreEmpresa}" ${accion} correctamente`, 'success');
    }
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
