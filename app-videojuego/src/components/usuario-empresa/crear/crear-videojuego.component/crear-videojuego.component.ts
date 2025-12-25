import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { CrearVideojuegoService } from '../../../../services/empresa/crear-videojuego.service';
import { VideojuegoRequestDto } from '../../../../models/dtos/empresa/videojuego/videojuego-request-dto';

interface Categoria {
  id: string;
  nombre: string;
}

@Component({
  selector: 'app-crear-videojuego.component',
  imports: [ReactiveFormsModule, CommonModule, SharePopupComponent],
  templateUrl: './crear-videojuego.component.html',
  styleUrl: './crear-videojuego.component.scss',
})
export class CrearVideojuegoComponent implements OnInit {
  nuevoVideojuego!: FormGroup;
  selectedFile: File | null = null;
  categoriasSeleccionadas: string[] = [];
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  categoriasDisponibles: Categoria[] = [
    { id: '1', nombre: 'Acción' },
    { id: '2', nombre: 'Aventura' },
    { id: '3', nombre: 'RPG' },
    { id: '4', nombre: 'Estrategia' },
    { id: '5', nombre: 'Deportes' },
    { id: '6', nombre: 'Racing' },
    { id: '7', nombre: 'Shooter' },
    { id: '8', nombre: 'Simulación' },
    { id: '9', nombre: 'Puzzle' },
    { id: '10', nombre: 'Plataformas' },
  ];

  constructor(
    private formBuilder: FormBuilder,
    private crearVideojuegoService: CrearVideojuegoService
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
      // Validar tipo de archivo
      const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png'];
      if (!allowedTypes.includes(file.type)) {
        this.showMessage('Solo se permiten archivos de imagen (JPG, JPEG, PNG)', 'error');
        return;
      }

      // Validar tamaño (máximo 5MB)
      const maxSize = 5 * 1024 * 1024; // 5MB
      if (file.size > maxSize) {
        this.showMessage('El archivo debe ser menor a 5MB', 'error');
        return;
      }

      this.selectedFile = file;
    }
  }

  //* Manejo de cambios en categorías */
  onCategoriaChange(event: any, categoriaId: string): void {
    if (event.target.checked) {
      this.categoriasSeleccionadas.push(categoriaId);
    } else {
      const index = this.categoriasSeleccionadas.indexOf(categoriaId);
      if (index > -1) {
        this.categoriasSeleccionadas.splice(index, 1);
      }
    }
  }

  //* Validar formulario antes del envío */
  private validarFormulario(): boolean {
    if (!this.nuevoVideojuego.valid) {
      this.showMessage('Por favor complete todos los campos requeridos', 'error');
      return false;
    }

    if (!this.selectedFile) {
      this.showMessage('Por favor seleccione una imagen para el videojuego', 'error');
      return false;
    }

    if (this.categoriasSeleccionadas.length === 0) {
      this.showMessage('Por favor seleccione al menos una categoría', 'error');
      return false;
    }

    return true;
  }

  //* Envío del formulario */
  submit(): void {
    if (!this.validarFormulario()) {
      return;
    }

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
        this.resetFormulario();
      },
      error: (error) => {
        const mensaje = error.error?.mensaje || 'Error al registrar el videojuego';
        this.showMessage(mensaje, 'error');
      },
    });
  }

  //* Mostrar mensajes */
  private showMessage(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }

  //* Resetear formulario */
  private resetFormulario(): void {
    this.nuevoVideojuego.reset();
    this.selectedFile = null;
    this.categoriasSeleccionadas = [];

    // Desmarcar checkboxes
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach((checkbox: any) => {
      checkbox.checked = false;
    });

    // Limpiar input de archivo
    const fileInput = document.getElementById('imagen') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  }
}
