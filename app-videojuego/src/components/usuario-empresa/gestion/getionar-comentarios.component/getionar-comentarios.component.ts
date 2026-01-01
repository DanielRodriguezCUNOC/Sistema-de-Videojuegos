import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { EditarEstadoVideojuegoResponseDto } from '../../../../models/dtos/empresa/comentario/editar-estado-videojuego-response-dto';
import { EditarEstadoEmpresaResponseDto } from '../../../../models/dtos/empresa/comentario/editar-estado-empresa-response-dto';
import { EditarEstadoEmpresaRequestDto } from '../../../../models/dtos/empresa/comentario/editar-estado-empresa-request-dto';
import { EditarEstadoVideojuegoRequestDto } from '../../../../models/dtos/empresa/comentario/editar-estado-videojuego-request-dto';
import { GestionarComentariosService } from '../../../../services/empresa/comentarios/getionar-comentarios.service';
import { MasterLoginService } from '../../../../services/login/masterlogin.service';

@Component({
  selector: 'app-getionar-comentarios.component',
  imports: [SharePopupComponent, FormsModule],
  templateUrl: './getionar-comentarios.component.html',
  styleUrl: './getionar-comentarios.component.scss',
})
export class GetionarComentariosComponent implements OnInit {
  videojuegos: EditarEstadoVideojuegoResponseDto[] = [];
  estadoComentarioGeneral: EditarEstadoEmpresaResponseDto = {
    estadoComentarioGeneral: false,
    idEmpresa: 0,
    nombreEmpresa: '',
  };
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;
  cambiosPendientes = false;
  isLoading = false;

  constructor(
    private router: Router,
    private service: GestionarComentariosService,
    private masterLoginService: MasterLoginService
  ) {}

  ngOnInit(): void {}

  cargarDatosComentarios(): void {
    const idUsuario = this.masterLoginService.getUserId();
    if (!idUsuario) {
      this.mostrarPopup('Error: Usuario no autenticado', 'error');
      this.router.navigate(['/login']);
      return;
    }

    this.isLoading = true;

    this.service.obtenerEstadoComentarioEmpresa(idUsuario).subscribe({
      next: (estadoEmpresa) => {
        this.estadoComentarioGeneral = estadoEmpresa;

        this.service.obtenerEstadoComentarioVideojuego(idUsuario).subscribe({
          next: (videojuegos) => {
            this.videojuegos = videojuegos;
            this.isLoading = false;
            this.mostrarPopup('Datos cargados correctamente', 'success');
          },
          error: (error) => {
            console.error('Error al cargar videojuegos:', error);
            this.isLoading = false;
            this.mostrarPopup('Error al cargar los videojuegos', 'error');
          },
        });
      },
      error: (error) => {
        console.error('Error al cargar estado de empresa:', error);
        this.isLoading = false;
        this.mostrarPopup('Error al cargar el estado de la empresa', 'error');
      },
    });
  }

  regresar(): void {
    this.router.navigate(['/user-empresa/']);
  }

  cambiarEstadoGeneral(event: any): void {
    const nuevoEstado = event.target.checked;
    const estadoAnterior = this.estadoComentarioGeneral.estadoComentarioGeneral;

    //* Actualizar solo el estado local del toggle
    this.estadoComentarioGeneral.estadoComentarioGeneral = nuevoEstado;

    //* Guardar el estado general en el servidor
    const request: EditarEstadoEmpresaRequestDto = {
      idEmpresa: this.estadoComentarioGeneral.idEmpresa,
      estadoComentarioGeneral: nuevoEstado,
    };

    //* Enviar la solicitud al servidor
    this.service.actualizarEstadoComentarioEmpresa(request).subscribe({
      next: () => {
        const mensaje = nuevoEstado
          ? 'Comentarios generales habilitados para todos los videojuegos'
          : 'Comentarios generales deshabilitados para todos los videojuegos';
        this.mostrarPopup(mensaje, 'success');

        //* Recargar los datos para obtener los estados actualizados desde el backend
        this.cargarDatosComentarios();
      },
      error: (error) => {
        console.error('Error al actualizar estado general:', error);
        //* Revertir el estado del toggle
        this.estadoComentarioGeneral.estadoComentarioGeneral = estadoAnterior;
        this.mostrarPopup('Error al actualizar el estado general', 'error');
      },
    });
  }

  cambiarEstadoVideojuego(idVideojuego: number, estadoActual: boolean): void {
    //* Encontrar el videojuego correspondiente usando el idVideojuego proporcionado
    const videojuego = this.videojuegos.find((v) => v.idVideojuego === idVideojuego);
    if (!videojuego) {
      this.mostrarPopup('Error: Videojuego no encontrado', 'error');
      return;
    }

    const nuevoEstado = !estadoActual;
    const estadoAnterior = videojuego.estadoComentarioVideojuego;

    //*Actualizar estado local
    videojuego.estadoComentarioVideojuego = nuevoEstado;

    //* Preparar la solicitud
    const request: EditarEstadoVideojuegoRequestDto = {
      idVideojuego: idVideojuego,
      estadoComentarioVideojuego: nuevoEstado,
    };

    //* Guardar en el servidor
    this.service.actualizarEstadoComentarioVideojuego(request).subscribe({
      next: () => {
        const accion = nuevoEstado ? 'habilitados' : 'deshabilitados';
        this.mostrarPopup(`Comentarios ${accion} para "${videojuego.titulo}"`, 'success');
      },
      error: (error) => {
        console.error('Error al actualizar estado del videojuego:', error);
        //* Revertir el cambio en caso de error
        videojuego.estadoComentarioVideojuego = estadoAnterior;
        this.mostrarPopup(
          `Error al actualizar el estado del videojuego "${videojuego.titulo}"`,
          'error'
        );
      },
    });
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
