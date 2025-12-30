import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';

@Component({
  selector: 'app-crear-comision-especifica.component',
  imports: [CommonModule, ReactiveFormsModule, SharePopupComponent],
  templateUrl: './crear-comision-especifica.component.html',
  styleUrl: './crear-comision-especifica.component.scss',
})
export class CrearComisionEspecificaComponent implements OnInit {
  comisionForm: FormGroup;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;
  isLoading = false;

  constructor(private formBuilder: FormBuilder, private router: Router) {
    this.comisionForm = this.formBuilder.group({
      nombreEmpresa: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
          Validators.pattern(/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s&.,()-]+$/),
        ],
      ],
      comision: [
        '',
        [
          Validators.required,
          Validators.min(0),
          Validators.max(100),
          Validators.pattern(/^\d{1,2}(\.\d{1,2})?$/),
        ],
      ],
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.comisionForm.valid) {
      this.isLoading = true;

      const comisionData = {
        nombreEmpresa: this.comisionForm.get('nombreEmpresa')?.value.trim(),
        comision: parseFloat(this.comisionForm.get('comision')?.value),
      };

      // TODO: Replace with actual service call
      // this.comisionService.asignarComision(comisionData).subscribe({
      //   next: (response) => {
      //     this.mostrarPopup('Comisión asignada exitosamente.', 'success');
      //     this.isLoading = false;
      //     this.comisionForm.reset();
      //
      //     setTimeout(() => {
      //       this.router.navigate(['/user-admin/gestionar-comisiones']);
      //     }, 1500);
      //   },
      //   error: (err) => {
      //     console.error('Error al asignar comisión:', err);
      //     this.mostrarPopup('Error al asignar la comisión. Verifique los datos.', 'error');
      //     this.isLoading = false;
      //   }
      // });

      // Simulated success for now
      setTimeout(() => {
        this.mostrarPopup(
          `Comisión del ${comisionData.comision}% asignada exitosamente a ${comisionData.nombreEmpresa}.`,
          'success'
        );
        this.isLoading = false;
        this.comisionForm.reset();

        setTimeout(() => {
          this.router.navigate(['/user-admin/gestionar-comisiones']);
        }, 2000);
      }, 1500);
    } else {
      this.mostrarPopup('Por favor, complete todos los campos correctamente.', 'error');
      this.marcarCamposInvalidos();
    }
  }

  limpiarFormulario(): void {
    this.comisionForm.reset();
    this.comisionForm.markAsUntouched();
  }

  regresar(): void {
    this.router.navigate(['/user-admin/gestionar-comisiones']);
  }

  // Getters para acceso fácil a los controles del formulario
  get nombreEmpresa() {
    return this.comisionForm.get('nombreEmpresa');
  }

  get comision() {
    return this.comisionForm.get('comision');
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }

  private marcarCamposInvalidos(): void {
    Object.keys(this.comisionForm.controls).forEach((key) => {
      const control = this.comisionForm.get(key);
      if (control && control.invalid) {
        control.markAsTouched();
      }
    });
  }
}
