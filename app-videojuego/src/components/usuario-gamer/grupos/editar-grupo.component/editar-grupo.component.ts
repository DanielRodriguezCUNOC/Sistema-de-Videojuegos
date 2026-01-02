import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { GrupoService } from '../../../../services/gamer/grupo/grupo.service';
import { EditarGrupoRequestDto } from '../../../../models/dtos/gamer/grupo/editar-grupo-request-dto';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';

@Component({
  selector: 'app-editar-grupo.component',
  imports: [ReactiveFormsModule, SharePopupComponent],
  templateUrl: './editar-grupo.component.html',
  styleUrl: './editar-grupo.component.scss',
})
export class EditarGrupoComponent implements OnInit {
  grupoForm!: FormGroup;
  private idGrupo: number = 0;
  private nombreActual: string = '';
  isLoading = false;

  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(
    private fb: FormBuilder,
    private grupoService: GrupoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initializeForm();
    this.obtenerParametros();
  }

  private initializeForm(): void {
    this.grupoForm = this.fb.group({
      nombreGrupo: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    });
  }

  obtenerParametros() {
    this.route.params.subscribe((params) => {
      this.idGrupo = +params['idGrupo'];
      this.nombreActual = params['nombreGrupo'];

      this.grupoForm.patchValue({
        nombreGrupo: this.nombreActual,
      });
    });
  }

  editarGrupo(): void {
    if (this.grupoForm.invalid) {
      this.grupoForm.markAllAsTouched();
      this.mostrarPopup('Por favor, completa todos los campos correctamente.', 'error');
      return;
    }

    const nuevoNombre = this.grupoForm.get('nombreGrupo')?.value?.trim();

    if (nuevoNombre === this.nombreActual) {
      this.mostrarPopup('No hay cambios para guardar.', 'info');
      return;
    }

    this.isLoading = true;

    const data: EditarGrupoRequestDto = {
      idGrupo: this.idGrupo,
      nuevoNombreGrupo: nuevoNombre,
    };

    this.grupoService.editarGrupo(data).subscribe({
      next: () => {
        this.isLoading = false;
        this.mostrarPopup('Grupo editado correctamente', 'success');
        setTimeout(() => {
          this.regresar();
        }, 1500);
      },
      error: (error) => {
        this.isLoading = false;
        this.mostrarPopup('Error al editar el grupo', 'error');
        console.error('Error al editar el grupo:', error);
      },
    });
  }

  regresar(): void {
    this.router.navigate(['/user-gamer/mis-grupos']);
  }
  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
