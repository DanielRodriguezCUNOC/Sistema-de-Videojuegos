import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { EstadoComentarioVideojuegoDto } from '../../../../models/dtos/empresa/comentario/estado-comentario-videojuego-dto';
import { ComentarioEmpresaRequestDTO } from '../../../../models/dtos/empresa/comentario/comentario-empresa-request-dto';

@Component({
  selector: 'app-getionar-comentarios.component',
  imports: [SharePopupComponent, FormsModule],
  templateUrl: './getionar-comentarios.component.html',
  styleUrl: './getionar-comentarios.component.scss',
})
export class GetionarComentariosComponent implements OnInit {
  videojuegos: EstadoComentarioVideojuegoDto[] = [];
  estadoComentarioGeneral: boolean = true;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;
  cambiosPendientes = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.cargarDatosComentarios();
  }

  cargarDatosComentarios(): void {
    this.videojuegos = [
      {
        idVideojuego: '1',
        titulo: 'Super Adventure Game',
        estadoComentarioVideojuego: true,
        estadoComentarioGeneral: true,
      },
      {
        idVideojuego: '2',
        titulo: 'Mystery Quest',
        estadoComentarioVideojuego: false,
        estadoComentarioGeneral: true,
      },
      {
        idVideojuego: '3',
        titulo: 'Space Explorer',
        estadoComentarioVideojuego: true,
        estadoComentarioGeneral: false,
      },
    ];

    this.estadoComentarioGeneral =
      this.videojuegos.length > 0 ? this.videojuegos[0].estadoComentarioGeneral : true;
  }

  regresar(): void {
    if (this.cambiosPendientes) {
      if (confirm('¿Tienes cambios sin guardar. ¿Estás seguro de que quieres salir?')) {
        this.router.navigate(['/user-empresa/empresa-module']);
      }
    } else {
      this.router.navigate(['/user-empresa/empresa-module']);
    }
  }

  cambiarEstadoGeneral(event: any): void {
    const nuevoEstado = event.target.checked;
    this.estadoComentarioGeneral = nuevoEstado;

    this.videojuegos.forEach((videojuego) => {
      videojuego.estadoComentarioGeneral = nuevoEstado;
    });

    this.cambiosPendientes = true;
    const mensaje = nuevoEstado
      ? 'Comentarios generales habilitados para todos los videojuegos'
      : 'Comentarios generales deshabilitados para todos los videojuegos';
    this.mostrarPopup(mensaje, 'info');
  }

  cambiarEstadoVideojuego(idVideojuego: string, estadoActual: boolean): void {
    const videojuego = this.videojuegos.find((v) => v.idVideojuego === idVideojuego);
    if (videojuego) {
      const nuevoEstado = !estadoActual;
      videojuego.estadoComentarioVideojuego = nuevoEstado;
      this.cambiosPendientes = true;

      const accion = nuevoEstado ? 'habilitados' : 'deshabilitados';
      this.mostrarPopup(`Comentarios ${accion} para "${videojuego.titulo}"`, 'success');
    }
  }

  guardarCambios(): void {
    if (!this.cambiosPendientes) {
      this.mostrarPopup('No hay cambios para guardar', 'info');
      return;
    }

    const requestData: ComentarioEmpresaRequestDTO = {
      estadosComentariosVideojuegos: this.videojuegos.map((videojuego) => ({
        idVideojuego: videojuego.idVideojuego,
        titulo: videojuego.titulo,
        estadoComentarioVideojuego: videojuego.estadoComentarioVideojuego,
        estadoComentarioGeneral: videojuego.estadoComentarioGeneral,
      })),
    };

    console.log('Datos a enviar:', requestData);

    setTimeout(() => {
      this.cambiosPendientes = false;
      this.mostrarPopup('Cambios guardados correctamente', 'success');
    }, 1000);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
