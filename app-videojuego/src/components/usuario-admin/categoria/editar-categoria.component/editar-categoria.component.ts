import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { CrudCategoriaService } from '../../../../services/admin/categoria/crud-categoria.service';
import { EditarCategoriaDTO } from '../../../../models/dtos/categoria/editar-categoria-dto';

@Component({
  selector: 'app-editar-categoria',
  imports: [CommonModule, ReactiveFormsModule, SharePopupComponent],
  templateUrl: './editar-categoria.component.html',
  styleUrl: './editar-categoria.component.scss',
})
export class EditarCategoriaComponent implements OnInit {
  editarForm: FormGroup;
  categoriaId: number = 0;
  categoriaNombre: string = '';
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;
  isLoading = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private service: CrudCategoriaService
  ) {
    this.editarForm = this.formBuilder.group({
      categoria: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    });
  }

  ngOnInit(): void {
    this.obtenerParametros();
  }

  obtenerParametros(): void {
    this.route.params.subscribe((params) => {
      this.categoriaId = +params['id'];
      this.categoriaNombre = params['categoria'];

      if (this.categoriaId && this.categoriaNombre) {
        this.editarForm.patchValue({
          categoria: this.categoriaNombre,
        });
      } else {
        this.mostrarPopup('Parámetros inválidos para editar la categoría.', 'error');
        this.regresar();
      }
    });
  }

  onSubmit(): void {
    if (this.editarForm.valid) {
      this.isLoading = true;

      const editarData: EditarCategoriaDTO = {
        idCategoria: this.categoriaId,
        categoria: this.editarForm.get('categoria')?.value.trim(),
      };

      this.service.actualizarCategoria(editarData).subscribe({
        next: () => {
          this.mostrarPopup('Categoría actualizada exitosamente.', 'success');
          this.isLoading = false;
          setTimeout(() => {
            this.redirigir();
          }, 1500);
        },
        error: (err) => {
          this.mostrarPopup('Error al actualizar la categoría.', 'error');
          this.isLoading = false;
        },
      });
    }
  }

  regresar(): void {
    this.router.navigate(['/admin-user/gestionar-categoria']);
  }

  get categoria() {
    return this.editarForm.get('categoria');
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }

  redirigir() {
    this.router.navigate(['/user-admin/gestionar-categorias']);
  }
}
