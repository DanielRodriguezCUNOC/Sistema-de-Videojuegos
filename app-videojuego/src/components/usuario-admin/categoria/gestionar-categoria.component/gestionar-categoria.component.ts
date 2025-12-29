import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { CategoriaResponseDTO } from '../../../../models/dtos/categoria/categoria-response-dto';
import { ListaCategoriaDTO } from '../../../../models/dtos/categoria/lista-categoria-dto';
import { CrudCategoriaService } from '../../../../services/admin/categoria/crud-categoria.service';

@Component({
  selector: 'app-gestionar-categoria.component',
  imports: [RouterLink, SharePopupComponent],
  templateUrl: './gestionar-categoria.component.html',
  styleUrl: './gestionar-categoria.component.scss',
})
export class GestionarCategoriaComponent implements OnInit {
  categorias: CategoriaResponseDTO[] = [];
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(private service: CrudCategoriaService, private router: Router) {}

  ngOnInit(): void {
    this.cargarCategorias();
  }

  cargarCategorias(): void {
    this.service.obtenerCategorias().subscribe({
      next: (data: ListaCategoriaDTO) => {
        this.categorias = data.categorias;
      },
      error: (err) => {
        this.mostrarPopup('Error al cargar las categorías.', 'error');
        this.popupMostrar = true;
      },
    });
  }
  eliminarCategoria(id: number): void {
    this.service.eliminarCategoria(id).subscribe({
      next: () => {
        this.mostrarPopup('Categoría eliminada correctamente.', 'success');
        this.cargarCategorias();
      },
      error: (err) => {
        this.mostrarPopup('Error al eliminar la categoría.', 'error');
        this.popupMostrar = true;
      },
    });
  }

  editarCategoria(id: number, categoria: string): void {
    this.router.navigate(['/user-admin/editar-categoria', id, categoria]);
  }

  regresar(): void {
    this.router.navigate(['/user-admin']);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
