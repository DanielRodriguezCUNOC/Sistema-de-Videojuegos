import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { ComisionService } from '../../../../services/admin/comision/comision.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EditarComisionGlobalDto } from '../../../../models/dtos/comision/editar-comision-global-dto';

@Component({
  selector: 'app-editar-comision',
  imports: [ReactiveFormsModule, SharePopupComponent],
  templateUrl: './editar-comision.component.html',
  styleUrl: './editar-comision.component.scss',
})
export class EditarComisionComponent implements OnInit {
  editarForm: FormGroup;
  comisionId: number = 0;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;
  isLoading = false;
  comisionActual: number = 0;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private service: ComisionService
  ) {
    this.editarForm = new FormGroup({
      comision: this.formBuilder.control('', []),
    });
  }

  ngOnInit(): void {
    this.obtenerParametros();
  }

  obtenerParametros(): void {
    this.route.params.subscribe((params) => {
      this.comisionActual = params['comisionGlobal'];
      this.comisionId = +params['comisionId'];

      if (this.comisionActual && this.comisionId) {
        this.editarForm.patchValue({
          comision: this.comisionActual,
        });
      } else {
        this.mostrarPopup('Parámetros inválidos para editar la categoría.', 'error');
        this.regresar();
      }
    });
  }

  onSubmit() {
    if (this.editarForm.valid) {
      this.isLoading = true;

      const editarData: EditarComisionGlobalDto = {
        id: this.comisionId,
        comision: this.editarForm.value.comision,
      };

      this.service.editarComisionGlobal(editarData).subscribe({
        next: () => {
          this.mostrarPopup('Comisión global editada correctamente.', 'success');
          this.isLoading = false;
          setTimeout(() => {
            this.router.navigate(['/user-admin/gestionar-comisiones']);
          }, 1500);
        },
        error: (err) => {
          this.isLoading = false;
          this.mostrarPopup('Error al editar la comisión global.', 'error');
        },
      });
    }
  }

  regresar() {
    this.router.navigate(['/user-admin/comision-global']);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
