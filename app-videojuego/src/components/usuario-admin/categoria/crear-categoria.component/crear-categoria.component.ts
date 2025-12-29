import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { CrudCategoriaService } from '../../../../services/admin/categoria/crud-categoria.service';
import { CrearCategoriaDTO } from '../../../../models/dtos/categoria/crear-categoria-dto';

@Component({
  selector: 'app-crear-categoria',
  imports: [CommonModule, ReactiveFormsModule, SharePopupComponent],
  templateUrl: './crear-categoria.component.html',
  styleUrl: './crear-categoria.component.scss',
})
export class CrearCategoriaComponent implements OnInit {
  crearForm: FormGroup;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;
  isLoading = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private service: CrudCategoriaService
  ) {
    this.crearForm = this.formBuilder.group({
      categoria: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.crearForm.valid) {
      this.isLoading = true;

      const crearData: CrearCategoriaDTO = {
        categoria: this.crearForm.get('categoria')?.value.trim(),
      };

      this.service.crearCategoria(crearData).subscribe({
        next: () => {
          this.mostrarPopup('Categoría creada exitosamente.', 'success');
          this.isLoading = false;
          this.crearForm.reset();
          this.limpiarFormulario();
          this.redirigir();
        },
        error: (err) => {
          console.error('Error al crear categoría:', err);
          this.mostrarPopup('Error al crear la categoría.', 'error');
          this.isLoading = false;
        },
      });
    }
  }

  limpiarFormulario(): void {
    this.crearForm.reset();
    this.crearForm.markAsUntouched();
  }

  regresar(): void {
    this.router.navigate(['/user-admin/gestionar-categorias']);
  }

  get categoria() {
    return this.crearForm.get('categoria');
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
