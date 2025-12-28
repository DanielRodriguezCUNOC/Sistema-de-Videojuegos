import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { CrearVideojuegoService } from '../../../../services/empresa/videojuego/crear-videojuego.service';
import { VideojuegoRequestDto } from '../../../../models/dtos/empresa/videojuego/videojuego-request-dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-crear-videojuego.component',
  imports: [ReactiveFormsModule, CommonModule, FormsModule, SharePopupComponent],
  templateUrl: './crear-videojuego.component.html',
  styleUrl: './crear-videojuego.component.scss',
})
export class CrearVideojuegoComponent implements OnInit {
  nuevoVideojuego!: FormGroup;
  selectedFile: File | null = null;
  categoriasSeleccionadas: string[] = [];
  nuevaCategoria: string = '';
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(
    private formBuilder: FormBuilder,
    private crearVideojuegoService: CrearVideojuegoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.nuevoVideojuego = this.formBuilder.group({
      titulo: ['', [Validators.required, Validators.minLength(3)]],
      descripcion: ['', [Validators.required, Validators.minLength(10)]],
      fechaLanzamiento: ['', [Validators.required]],
      precio: ['', [Validators.required, Validators.min(0)]],
      recursosMinimos: ['', [Validators.required, Validators.minLength(20)]],
      clasificacion: ['', [Validators.required]],
    });
  }

  //* Manejo de la selección de archivo */
  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  //* Agregar nueva categoría */
  agregarCategoria(): void {
    const categoria = this.nuevaCategoria.trim();

    if (!categoria) {
      return;
    }

    const categoriaExiste = this.categoriasSeleccionadas.some(
      (cat) => cat.toLowerCase() === categoria.toLowerCase()
    );

    if (categoriaExiste) {
      this.showMessage('Esta categoría ya ha sido agregada', 'error');
      return;
    }

    this.categoriasSeleccionadas.push(categoria);
    this.nuevaCategoria = '';
  }

  //* Eliminar categoria */
  eliminarCategoria(index: number): void {
    this.categoriasSeleccionadas.splice(index, 1);
  }

  //* Enviar formulario */
  submit(): void {
    if (this.nuevoVideojuego.valid) {
      const datosVideojuego: VideojuegoRequestDto = {
        idUsuarioEmpresa: localStorage.getItem('idUsuarioEmpresa') || '0',
        titulo: this.nuevoVideojuego.value.titulo,
        descripcion: this.nuevoVideojuego.value.descripcion,
        fechaLanzamiento: new Date(this.nuevoVideojuego.value.fechaLanzamiento),
        precio: parseFloat(this.nuevoVideojuego.value.precio),
        recurosMinimos: this.nuevoVideojuego.value.recursosMinimos,
        imagen: this.selectedFile!,
        clasificacion: this.nuevoVideojuego.value.clasificacion,
        categorias: this.categoriasSeleccionadas,
      };

      this.crearVideojuegoService.crearVideojuego(datosVideojuego).subscribe({
        next: (response) => {
          this.showMessage('Videojuego registrado correctamente', 'success');
          this.resetFormularioInterno();
        },
        error: (error) => {
          const mensaje = error.error?.mensaje || 'Error al registrar el videojuego';
          this.showMessage(mensaje, 'error');
        },
      });
    }
  }

  //* Mostrar mensajes */
  private showMessage(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }

  //* Resetear formulario */
  resetFormulario(): void {
    this.nuevoVideojuego.reset();
    this.selectedFile = null;
    this.categoriasSeleccionadas = [];
    this.nuevaCategoria = '';

    // Limpiar input de archivo
    const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  }

  //* Resetear formulario privado - para uso interno */
  private resetFormularioInterno(): void {
    this.resetFormulario();
  }

  regresar(): void {
    this.router.navigate(['/user-empresa']);
  }
}
