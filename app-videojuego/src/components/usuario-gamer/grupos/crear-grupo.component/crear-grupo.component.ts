import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { GrupoService } from '../../../../services/gamer/grupo/grupo.service';
import { MasterLoginService } from '../../../../services/login/masterlogin.service';
import { GrupoRequestDto } from '../../../../models/dtos/gamer/grupo/grupo-request-dto';

@Component({
  selector: 'app-crear-grupo.component',
  imports: [ReactiveFormsModule, SharePopupComponent],
  templateUrl: './crear-grupo.component.html',
  styleUrl: './crear-grupo.component.scss',
})
export class CrearGrupoComponent implements OnInit {
  grupoForm!: FormGroup;
  isLoading = false;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private grupoService: GrupoService,
    private masterLoginService: MasterLoginService
  ) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  private initializeForm(): void {
    this.grupoForm = this.fb.group({
      nombreGrupo: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(50),
          Validators.pattern(/^[a-zA-Z0-9\s\-_áéíóúÁÉÍÓÚñÑ]+$/),
        ],
      ],
    });
  }

  crearGrupo(): void {
    if (this.grupoForm.invalid) {
      this.grupoForm.markAllAsTouched();
      this.mostrarPopup('Por favor, completa todos los campos correctamente.', 'error');
      return;
    }

    const userId = this.masterLoginService.getUserId();
    if (!userId) {
      this.mostrarPopup(
        'No se pudo obtener la información del usuario. Por favor, inicia sesión nuevamente.',
        'error'
      );
      return;
    }

    this.isLoading = true;

    const grupoData: GrupoRequestDto = {
      idCreador: userId,
      nombreGrupo: this.grupoForm.get('nombreGrupo')?.value?.trim(),
    };

    this.grupoService.registrarGrupo(grupoData).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.mostrarPopup(
          '¡Grupo creado exitosamente! Ahora puedes invitar a otros gamers.',
          'success'
        );

        // Redirigir después de un breve delay para mostrar el mensaje
        setTimeout(() => {
          this.router.navigate(['/user-gamer/mis-grupos']);
        }, 2000);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Error al crear grupo:', error);

        let errorMessage = 'Error al crear el grupo. Por favor, intenta nuevamente.';

        if (error.status === 400) {
          errorMessage = 'Los datos del grupo no son válidos. Verifica la información ingresada.';
        } else if (error.status === 409) {
          errorMessage = 'Ya existe un grupo con ese nombre. Por favor, elige otro nombre.';
        } else if (error.status === 401) {
          errorMessage = 'Tu sesión ha expirado. Por favor, inicia sesión nuevamente.';
        }

        this.mostrarPopup(errorMessage, 'error');
      },
    });
  }

  regresar(): void {
    if (this.grupoForm.dirty && !this.isLoading) {
      if (confirm('¿Estás seguro de que quieres salir? Los cambios no guardados se perderán.')) {
        this.router.navigate(['/user-gamer/mis-grupos']);
      }
    } else {
      this.router.navigate(['/user-gamer/mis-grupos']);
    }
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
