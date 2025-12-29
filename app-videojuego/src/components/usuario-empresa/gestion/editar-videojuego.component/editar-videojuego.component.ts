import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { GestionVideojuegosService } from '../../../../services/empresa/videojuego/gestion-videojuegos.service';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { EditarVideojuegoRequestDto } from '../../../../models/dtos/empresa/videojuego/editar-videojuego-request-dto';

@Component({
  selector: 'app-editar-videojuego',
  imports: [ReactiveFormsModule, CommonModule, SharePopupComponent],
  templateUrl: './editar-videojuego.component.html',
  styleUrl: './editar-videojuego.component.scss',
})
export class EditarVideojuegoComponent implements OnInit {
  editarForm: FormGroup;
  idVideojuego: string = '';
  tituloVideojuego: string = '';
  descripcionVideojuego: string = '';
  precioVideojuego: number = 0;
  recursosMinimosVideojuego: string = '';
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private gestionVideojuegosService: GestionVideojuegosService
  ) {
    this.editarForm = this.formBuilder.group({
      titulo: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(60)]],
      descripcion: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(600)]],
      precio: ['', [Validators.required, Validators.min(0)]],
      recursosMinimos: [
        '',
        [Validators.required, Validators.minLength(20), Validators.maxLength(200)],
      ],
    });
  }

  ngOnInit(): void {
    this.obtenerParametros();
    this.obtenerVideojuego();
  }

  obtenerParametros(): void {
    this.route.params.subscribe((params) => {
      this.idVideojuego = params['idVideojuego'];

      if (!this.idVideojuego) {
        this.mostrarPopup('Parámetros inválidos para editar el videojuego.', 'error');
        this.regresar();
      }
    });
  }

  obtenerVideojuego(): void {
    this.gestionVideojuegosService
      .obtenerVideojuegoPorId(this.idVideojuego)
      .subscribe((videojuego) => {
        this.tituloVideojuego = videojuego.titulo;
        this.descripcionVideojuego = videojuego.descripcion;
        this.precioVideojuego = videojuego.precio;
        this.recursosMinimosVideojuego = videojuego.recurosMinimos;

        this.editarForm.patchValue({
          titulo: this.tituloVideojuego,
          descripcion: this.descripcionVideojuego,
          precio: this.precioVideojuego,
          recursosMinimos: this.recursosMinimosVideojuego,
        });
      });
  }

  onSubmit(): void {
    if (this.editarForm.valid) {
      const titulo = this.editarForm.get('titulo')?.value;
      const descripcion = this.editarForm.get('descripcion')?.value;
      const precio = this.editarForm.get('precio')?.value;
      const recursosMinimos = this.editarForm.get('recursosMinimos')?.value;

      const data: EditarVideojuegoRequestDto = {
        idVideojuego: +this.idVideojuego,
        titulo: titulo,
        descripcion: descripcion,
        precio: precio,
        recurosMinimos: recursosMinimos,
      };

      this.gestionVideojuegosService.editarVideojuego(this.idVideojuego, data).subscribe({
        next: () => {
          this.mostrarPopup('Videojuego editado exitosamente.', 'success');
          setTimeout(() => {
            this.regresar();
          }, 2000);
        },
        error: (error) => {
          this.mostrarPopup('Error al editar el videojuego', 'error');
        },
      });
    } else {
      this.mostrarPopup('Por favor, complete todos los campos del formulario.', 'error');
    }
  }

  regresar(): void {
    this.router.navigate(['/user-empresa/gestionar-videojuegos']);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
