import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PerfilVideojuegoService } from '../../../services/perfiles/perfil-videojuego.service';
import { SharePopupComponent } from '../../../shared/share-popup.component/share-popup.component';
import { PerfilVideojuegoResponseDTO } from '../../../models/dtos/videojuego/perfil-videojuego-response-dto';
import { ConvertirImagen } from '../../../utils/convertir-imagen';

@Component({
  selector: 'app-perfil-videojuego.component',
  imports: [CommonModule, SharePopupComponent],
  templateUrl: './perfil-videojuego.component.html',
  styleUrl: './perfil-videojuego.component.scss',
})
export class PerfilVideojuegoComponent implements OnInit {
  private convertirImagen = new ConvertirImagen();
  perfilVideojuego: PerfilVideojuegoResponseDTO | null = null;
  idVideojuego: number = 0;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private perfilVideojuegoService: PerfilVideojuegoService
  ) {}
  ngOnInit(): void {
    this.obtenerParametros();
    this.obtenerPerfilVideojuego();
  }

  obtenerPerfilVideojuego(): void {
    this.perfilVideojuegoService.obtenerPerfilVideojuego(this.idVideojuego).subscribe({
      next: (data) => {
        this.perfilVideojuego = data;
      },
      error: (error) => {
        this.mostrarPopup('Error al cargar el perfil del videojuego.', 'error');
        console.error('Error al cargar el perfil del videojuego:', error);
        this.regresar();
      },
    });
  }

  obtenerParametros(): void {
    this.route.params.subscribe((params) => {
      this.idVideojuego = +params['idVideojuego'];

      if (!this.idVideojuego) {
        this.mostrarPopup('Parámetros inválidos para ver el videojuego.', 'error');
        this.regresar();
      }
    });
  }

  regresar() {
    this.router.navigate(['/user-gamer']);
  }

  obtenerImagenBase64(imagenBase64: string): string {
    if (!imagenBase64) return '';
    return this.convertirImagen.createImageDataUrl(imagenBase64);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
