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
      nombreGrupo: ['', [Validators.required, Validators.minLength(3)]],
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
      next: () => {
        this.isLoading = false;
        this.mostrarPopup(
          '¡Grupo creado exitosamente! Ahora puedes invitar a otros gamers.',
          'success'
        );
        setTimeout(() => {
          this.router.navigate(['/user-gamer/mis-grupos']);
        }, 2000);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Error al crear grupo:', error);

        this.mostrarPopup('Error al crear el grupo. Por favor, intenta nuevamente.', 'error');
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
