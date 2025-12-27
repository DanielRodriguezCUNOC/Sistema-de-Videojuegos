import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { ComisionService } from '../../../../services/admin/comision/comision.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EditarComisionEspecificaDTO } from '../../../../models/dtos/comision/editar-comision-especifica-dto';

@Component({
  selector: 'app-editar-comision-especifica',
  imports: [ReactiveFormsModule, SharePopupComponent],
  templateUrl: './editar-comision-especifica.component.html',
  styleUrl: './editar-comision-especifica.component.scss',
})
export class EditarComisionEspecificaComponent implements OnInit {
  editarForm: FormGroup;
  empresaId: number = 0;
  nombreEmpresa: string = '';
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;
  isLoading = false;
  comision: number = 0;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private service: ComisionService
  ) {
    this.editarForm = this.formBuilder.group({
      comision: ['', [Validators.required, Validators.min(0), Validators.max(100)]],
    });
  }

  ngOnInit(): void {
    this.obtenerParametros();
  }

  obtenerParametros(): void {
    this.route.params.subscribe((params) => {
      this.empresaId = +params['idEmpresa'];
      this.nombreEmpresa = params['nombreEmpresa'] || 'Sin nombre';
      this.comision = +params['comision'];

      if (this.empresaId && this.comision !== undefined) {
        this.editarForm.patchValue({
          comision: this.comision,
        });
      } else {
        this.mostrarPopup('Parámetros inválidos para editar la comisión específica.', 'error');
        this.regresar();
      }
    });
  }

  onSubmit() {
    if (this.editarForm.valid) {
      this.isLoading = true;

      const editarData: EditarComisionEspecificaDTO = {
        idEmpresa: this.empresaId,
        comision: this.editarForm.value.comision,
      };

      this.service.editarComisionEspecifica(editarData).subscribe({
        next: () => {
          this.mostrarPopup(
            `Comisión específica para ${this.nombreEmpresa} editada correctamente.`,
            'success'
          );
          this.isLoading = false;
          setTimeout(() => {
            this.router.navigate(['/user-admin/gestionar-comisiones']);
          }, 1500);
        },
        error: (err) => {
          this.isLoading = false;
          const mensaje = err.error?.mensaje || 'Error al editar la comisión específica.';
          this.mostrarPopup(mensaje, 'error');
        },
      });
    } else {
      this.mostrarPopup('Por favor complete todos los campos correctamente.', 'error');
    }
  }

  regresar() {
    this.router.navigate(['/user-admin/gestionar-comisiones']);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
