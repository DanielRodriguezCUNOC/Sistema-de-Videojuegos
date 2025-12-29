import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { VideojuegoGestionRequestDto } from '../../../../models/dtos/empresa/videojuego/videojuego-gestion-request-dto';
import { editarEstadoVideojuegoDto } from '../../../../models/dtos/empresa/videojuego/editar-estado-videojuego-dto';
import { GestionVideojuegosService } from '../../../../services/empresa/videojuego/gestion-videojuegos.service';

@Component({
  selector: 'app-getionar-catalogo-videojuegos.component',
  imports: [SharePopupComponent],
  templateUrl: './getionar-catalogo-videojuegos.component.html',
  styleUrl: './getionar-catalogo-videojuegos.component.scss',
})
export class GetionarCatalogoVideojuegosComponent implements OnInit {
  videojuegos: VideojuegoGestionRequestDto[] = [];
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(private router: Router, private gestionService: GestionVideojuegosService) {}

  ngOnInit(): void {
    this.cargarVideojuegos();
  }

  cargarVideojuegos(): void {
    this.gestionService.obtenerCatalogoVideojuegos().subscribe({
      next: (videojuegos) => {
        this.videojuegos = videojuegos.videojuegos;
      },
      error: (error) => {
        this.mostrarPopup('Error al cargar los videojuegos', 'error');
      },
    });
  }

  regresar(): void {
    this.router.navigate(['/user-empresa/empresa-module']);
  }

  crearVideojuego(): void {
    this.router.navigate(['/user-empresa/crear-videojuego']);
  }

  editarPortada(idVideojuego: string): void {
    this.router.navigate([`/user-empresa/editar-portada/${idVideojuego}`]);
  }

  editarVideojuego(idVideojuego: string): void {
    this.router.navigate([`/user-empresa/editar-videojuego/${idVideojuego}`]);
  }

  cambiarVisibilidad(idVideojuego: string, estadoActual: boolean): void {
    const data: editarEstadoVideojuegoDto = {
      idVideojuego: Number(idVideojuego),
      nuevoEstado: !estadoActual,
    };

    this.gestionService.cambiarVisibilidadVideojuego(data).subscribe({
      next: () => {
        this.mostrarPopup(
          `Visibilidad del videojuego ${idVideojuego} cambiada exitosamente`,
          'success'
        );
        this.cargarVideojuegos();
      },
      error: () => {
        this.mostrarPopup(
          `Error al cambiar la visibilidad del videojuego ${idVideojuego}`,
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
