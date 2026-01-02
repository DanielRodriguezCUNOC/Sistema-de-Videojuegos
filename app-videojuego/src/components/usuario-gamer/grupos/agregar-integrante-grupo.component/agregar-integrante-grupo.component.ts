import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IntegranteGrupoService } from '../../../../services/gamer/grupo/integrante-grupo.service';
import { NuevoIntegranteRequestDto } from '../../../../models/dtos/gamer/grupo/nuevo-integrante-request-dto';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';

@Component({
  selector: 'app-agregar-integrante-grupo.component',
  imports: [ReactiveFormsModule, SharePopupComponent],
  templateUrl: './agregar-integrante-grupo.component.html',
  styleUrl: './agregar-integrante-grupo.component.scss',
})
export class AgregarIntegranteGrupoComponent implements OnInit {
  integranteForm!: FormGroup;
  private idGrupo: number = 0;
  isLoading = false;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(
    private fb: FormBuilder,
    private integranteGrupoService: IntegranteGrupoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initializeForm();
    this.obtenerParametros();
  }

  private initializeForm(): void {
    this.integranteForm = this.fb.group({
      nickname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
    });
  }

  private obtenerParametros(): void {
    this.route.params.subscribe((params) => {
      this.idGrupo = +params['idGrupo'];
    });
  }

  agregarIntegrante(): void {
    if (this.integranteForm.invalid) {
      this.mostrarPopup('Por favor, completa todos los campos correctamente.', 'error');
      return;
    }

    this.isLoading = true;

    const data: NuevoIntegranteRequestDto = {
      idGrupo: this.idGrupo,
      nickname: this.integranteForm.get('nickname')?.value?.trim(),
    };

    this.integranteGrupoService.agregarIntegrante(data).subscribe({
      next: () => {
        this.isLoading = false;
        this.mostrarPopup('Integrante agregado correctamente', 'success');
        setTimeout(() => {
          this.regresar();
        }, 1500);
      },
      error: (error) => {
        this.isLoading = false;
        this.mostrarPopup(
          'Error al agregar el integrante. Verifica que el nickname exista.',
          'error'
        );
        console.error('Error al agregar integrante:', error);
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
