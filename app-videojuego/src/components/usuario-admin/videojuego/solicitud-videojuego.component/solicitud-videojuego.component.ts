import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { SolicitudVideojuegoService } from '../../../../services/admin/solicitud/solicitud-videojuego.service';
import { SolicitudVideojuegoResponseDTO } from '../../../../models/dtos/administrador/videojuego/solicitud-videojuego-response-dto';
import { ListaSolicitudVideojuegoDTO } from '../../../../models/dtos/administrador/videojuego/lista-solicitud-videojuego-dto';
import { SolicitudVideojuegoRequestDTO } from '../../../../models/dtos/administrador/videojuego/solicitud-videojuego-request-dto';

@Component({
  selector: 'app-solicitud-videojuego.component',
  imports: [CommonModule, SharePopupComponent],
  templateUrl: './solicitud-videojuego.component.html',
  styleUrl: './solicitud-videojuego.component.scss',
})
export class SolicitudVideojuegoComponent implements OnInit {
  solicitudes: SolicitudVideojuegoResponseDTO[] = [];
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(private router: Router, private solicitudService: SolicitudVideojuegoService) {}

  ngOnInit(): void {
    this.cargarSolicitudes();
  }

  cargarSolicitudes(): void {
    this.solicitudService.obtenerSolicitudes().subscribe({
      next: (data: SolicitudVideojuegoResponseDTO[]) => {
        this.solicitudes = data;
        console.log('Solicitudes cargadas:', this.solicitudes.length);
      },
      error: (error) => {
        this.mostrarPopup(
          'Error al cargar las solicitudes. Por favor, intente nuevamente.',
          'error'
        );
        console.error('Error al obtener solicitudes:', error);
      },
    });
  }

  aprobarSolicitud(idSolicitud: number, idVideojuego: number, nombreCategoria: string): void {
    const data: SolicitudVideojuegoRequestDTO = {
      idSolicitud: idSolicitud,
      estado: 'aprobado',
      idVideojuego: idVideojuego,
      categoria: nombreCategoria,
    };

    console.log('Datos para aprobar solicitud:', data);

    this.solicitudService.gestionarSolicitud(data).subscribe({
      next: () => {
        this.mostrarPopup('Solicitud aprobada correctamente.', 'success');
        this.cargarSolicitudes();
      },
      error: (error) => {
        this.mostrarPopup('Error al aprobar la solicitud. Por favor, intente nuevamente.', 'error');
        console.error('Error al aprobar solicitud:', error);
      },
    });
  }

  rechazarSolicitud(idSolicitud: number, idVideojuego: number, nombreCategoria: string): void {
    const data: SolicitudVideojuegoRequestDTO = {
      idSolicitud: idSolicitud,
      estado: 'rechazado',
      idVideojuego: idVideojuego,
      categoria: nombreCategoria,
    };

    this.solicitudService.gestionarSolicitud(data).subscribe({
      next: () => {
        this.mostrarPopup('Solicitud rechazada correctamente.', 'success');
        this.cargarSolicitudes();
      },
      error: (error) => {
        this.mostrarPopup(
          'Error al rechazar la solicitud. Por favor, intente nuevamente.',
          'error'
        );
        console.error('Error al rechazar solicitud:', error);
      },
    });
  }

  regresar(): void {
    this.router.navigate(['/user-admin/admin-module']);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
