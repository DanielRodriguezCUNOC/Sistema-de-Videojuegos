import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CategoriaResponseDTO } from '../../../models/dtos/categoria/categoria-response-dto';
import { DatosVideojuegoTiendaDTO } from '../../../models/dtos/videojuego/datos-videojuego-tienda-dto';
import { CrudCategoriaService } from '../../../services/admin/categoria/crud-categoria.service';
import { TiendaService } from '../../../services/tienda/tienda.service';
import { ConvertirImagen } from '../../../utils/convertir-imagen';
import { SharePopupComponent } from '../../../shared/share-popup.component/share-popup.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-modulo-tienda.component',
  imports: [CommonModule, FormsModule, SharePopupComponent],
  templateUrl: './modulo-tienda.component.html',
  styleUrl: './modulo-tienda.component.scss',
})
export class ModuloTiendaComponent implements OnInit {
  private convertirImagen: ConvertirImagen = new ConvertirImagen();
  videojuegos: DatosVideojuegoTiendaDTO[] = [];
  videojuegosFiltrados: DatosVideojuegoTiendaDTO[] = [];
  categorias: CategoriaResponseDTO[] = [];
  hayMasResultados: boolean = true;
  //* indicamos desde qué posición cargar más resultados */
  offset: number = 0;
  //* cantidad de resultados a cargar por vez */
  limit: number = 2;
  textoBusqueda: string = '';
  //! 0 = todas las categorías
  categoriaSeleccionada: number = 0;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(
    private tiendaService: TiendaService,
    private categoriaService: CrudCategoriaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarCategorias();
    this.cargarVideojuegos();
  }

  cargarCategorias(): void {
    this.categoriaService.obtenerCategorias().subscribe({
      next: (data) => {
        this.categorias = data.categorias;
      },
      error: (error) => {
        console.error('Error al cargar categorías:', error);
      },
    });
  }

  cargarVideojuegos(): void {
    this.offset = 0;
    this.videojuegos = [];

    this.tiendaService.obtenerVideojuegos(this.offset, this.limit).subscribe({
      next: (data) => {
        this.videojuegos = data;
        this.videojuegosFiltrados = data;
        this.hayMasResultados = data.length === this.limit;
        this.offset += this.limit;
      },
      error: (error) => {
        console.error('Error al cargar videojuegos:', error);
      },
    });
  }

  buscarVideojuegoPorTitulo(): void {
    this.tiendaService.obtenerVideojuegoPorTitulo(this.textoBusqueda).subscribe({
      next: (data) => {
        this.videojuegosFiltrados = data ? [data] : [];
        if (this.videojuegosFiltrados.length === 0) {
          this.mostrarPopup('No se encontraron videojuegos con ese nombre.', 'info');
        }
      },
      error: (error) => {
        this.mostrarPopup('Error al buscar videojuegos. Por favor, intente nuevamente.', 'error');
        console.error('Error al buscar videojuegos:', error);
      },
    });
  }

  buscarVideojuegosPorCategoria(): void {
    const idCategoria = Number(this.categoriaSeleccionada);
    this.textoBusqueda = '';
    if (idCategoria === 0) {
      this.cargarVideojuegos();
      return;
    }

    this.tiendaService.obtenerVideojuegosPorCategoria(idCategoria).subscribe({
      next: (data) => {
        this.videojuegosFiltrados = data;
      },
      error: (error) => {
        this.mostrarPopup(
          'Error al filtrar por categoría. Por favor, intente nuevamente.',
          'error'
        );
        console.error('Error al filtrar por categoría:', error);
      },
    });
  }

  cargarMasVideojuegos(): void {
    if (!this.hayMasResultados) {
      this.mostrarPopup('No hay más videojuegos para cargar.', 'info');
      return;
    }
    this.tiendaService.obtenerVideojuegos(this.offset, this.limit).subscribe({
      next: (data) => {
        this.videojuegos = [...this.videojuegos, ...data];
        this.videojuegosFiltrados = this.videojuegos;
        this.hayMasResultados = data.length === this.limit;
        this.offset += this.limit;
      },
      error: (error) => {
        this.mostrarPopup(
          'Error al cargar más videojuegos. Por favor, intente nuevamente.',
          'error'
        );
        console.error('Error al cargar más videojuegos:', error);
      },
    });
  }

  irAlVideojuego(videojuegoId: number): void {
    this.router.navigate(['/perfil/videojuego', videojuegoId]);
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
