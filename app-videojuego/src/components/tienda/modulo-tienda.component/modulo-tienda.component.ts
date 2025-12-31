import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CategoriaResponseDTO } from '../../../models/dtos/categoria/categoria-response-dto';
import { DatosVideojuegoResponseDTO } from '../../../models/dtos/videojuego/DatosVideojuegoResponseDTO';
import { DatosVideojuegoTiendaDTO } from '../../../models/dtos/videojuego/datos-videojuego-tienda-dto';
import { CrudCategoriaService } from '../../../services/admin/categoria/crud-categoria.service';

@Component({
  selector: 'app-modulo-tienda.component',
  imports: [CommonModule, FormsModule],
  templateUrl: './modulo-tienda.component.html',
  styleUrl: './modulo-tienda.component.scss',
})
export class ModuloTiendaComponent implements OnInit {
  videojuegos: DatosVideojuegoTiendaDTO[] = [];
  videojuegosFiltrados: DatosVideojuegoTiendaDTO[] = [];
  categorias: CategoriaResponseDTO[] = [];
  hayMasResultados: boolean = true;
  //* indicamos desde qué posición cargar más resultados */
  offset: number = 0;
  //* cantidad de resultados a cargar por vez */
  limite: number = 2;
  textoBusqueda: string = '';
  //! 0 = todas las categorías
  categoriaSeleccionada: number = 0;

  constructor(
    private tiendaService: TiendaService,
    private categoriaService: CrudCategoriaService
  ) {}

  ngOnInit(): void {
    // Aquí se llamarían los servicios para obtener datos del backend
    this.cargarCategorias();
    this.cargarVideojuegos();
  }

  cargarCategorias(): void {
    // TODO: Llamar al servicio para obtener categorías
    // this.categoriaService.obtenerCategorias().subscribe(data => this.categorias = data);
  }

  cargarVideojuegos(): void {
    // TODO: Llamar al servicio para obtener videojuegos
    // this.videojuegoService.obtenerVideojuegos().subscribe(data => {
    //   this.videojuegos = data;
    //   this.videojuegosFiltrados = data;
    // });
  }

  buscarVideojuegos(): void {
    this.filtrarVideojuegos();
  }

  onCategoriaChange(): void {
    this.filtrarVideojuegos();
  }

  private filtrarVideojuegos(): void {
    let resultados = this.videojuegos;

    // Filtrar por texto
    if (this.textoBusqueda.trim()) {
      resultados = resultados.filter(
        (juego) =>
          juego.titulo.toLowerCase().includes(this.textoBusqueda.toLowerCase()) ||
          juego.descripcion.toLowerCase().includes(this.textoBusqueda.toLowerCase())
      );
    }

    // Filtrar por categoría
    if (this.categoriaSeleccionada > 0) {
      const categoriaSeleccionada = this.categorias.find(
        (c) => c.idCategoria === this.categoriaSeleccionada
      );
      if (categoriaSeleccionada) {
        resultados = resultados.filter((juego) =>
          juego.categorias.includes(categoriaSeleccionada.categoria)
        );
      }
    }

    this.videojuegosFiltrados = resultados;
  }

  // Método para convertir base64 a imagen
  obtenerImagenBase64(imagenBase64: string): string {
    if (!imagenBase64) return '';
    return `data:image/png;base64,${imagenBase64}`;
  }
}
