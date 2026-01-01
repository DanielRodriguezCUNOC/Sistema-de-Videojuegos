import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { BibliotecaService } from '../../../../services/gamer/biblioteca/biblioteca.service';
import { MasterLoginService } from '../../../../services/login/masterlogin.service';
import { VideojuegoCompradoResponseDto } from '../../../../models/dtos/gamer/biblioteca/videojuego-comprado-response-dto';
import { VideojuegoCompradoRequestDto } from '../../../../models/dtos/gamer/biblioteca/videojuego-comprado-request-dto';
import { ConvertirImagen } from '../../../../utils/convertir-imagen';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';

@Component({
  selector: 'app-videojuegos-comprados',
  imports: [CommonModule, FormsModule, SharePopupComponent],
  templateUrl: './videojuegos-comprados.component.html',
  styleUrl: './videojuegos-comprados.component.scss',
})
export class VideojuegosCompradosComponent implements OnInit {
  private convertirImagen: ConvertirImagen = new ConvertirImagen();
  videojuegosComprados: VideojuegoCompradoResponseDto[] = [];
  cargando: boolean = false;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(
    private bibliotecaService: BibliotecaService,
    private masterLoginService: MasterLoginService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarVideojuegosComprados();
  }

  cargarVideojuegosComprados(): void {
    const userId = this.masterLoginService.getUserId();

    if (!userId) {
      this.mostrarPopup(
        'No se pudo identificar el usuario. Por favor, inicie sesión nuevamente.',
        'error'
      );
      return;
    }

    this.cargando = true;

    this.bibliotecaService.obtenerListadoVideojuegos(userId).subscribe({
      next: (data) => {
        this.videojuegosComprados = data;
        this.cargando = false;

        if (data.length === 0) {
          this.mostrarPopup('No tienes videojuegos en tu biblioteca. ¡Visita la tienda!', 'info');
        }
      },
      error: (error) => {
        this.cargando = false;
        this.mostrarPopup(
          'Error al cargar tus videojuegos. Por favor, intente nuevamente.',
          'error'
        );
        console.error('Error al cargar videojuegos comprados:', error);
      },
    });
  }

  cambiarEstadoInstalacion(videojuego: VideojuegoCompradoResponseDto): void {
    const userId = this.masterLoginService.getUserId();

    if (!userId) {
      this.mostrarPopup('No se pudo identificar el usuario.', 'error');
      return;
    }

    const requestData: VideojuegoCompradoRequestDto = {
      idVideojuego: videojuego.idVideojuego,
      idUsuario: userId,
      estadoInstalacion: !videojuego.estadoInstalacion,
    };

    this.bibliotecaService.cambiarEstadoInstalacion(requestData).subscribe({
      next: () => {
        //* Actualizar el estado local despues de la confirmación del servidor
        videojuego.estadoInstalacion = !videojuego.estadoInstalacion;
        const mensaje = videojuego.estadoInstalacion
          ? 'Videojuego marcado como instalado'
          : 'Videojuego marcado como no instalado';
        this.mostrarPopup(mensaje, 'success');
      },
      error: (error) => {
        this.mostrarPopup('Error al cambiar el estado de instalación.', 'error');
        console.error('Error al cambiar estado de instalación:', error);
      },
    });
  }

  verDetallesVideojuego(idVideojuego: number): void {
    this.router.navigate(['/perfil/videojuego', idVideojuego]);
  }

  obtenerImagenBase64(imagenBase64: string): string {
    if (!imagenBase64) return '';
    return this.convertirImagen.createImageDataUrl(imagenBase64);
  }

  regresar(): void {
    this.router.navigate(['/user-gamer/']);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }

  irATienda(): void {
    this.router.navigate(['/user-gamer/tienda']);
  }
}
