import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { GestionEmpresaService } from '../../../../services/admin/empresa/gestion-empresa.service';
import { EditarEmpresaRequestDTO } from '../../../../models/dtos/administrador/empresa/editar-empresa-request-dto';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';

@Component({
  selector: 'app-editar-empresa',
  imports: [SharePopupComponent, ReactiveFormsModule],
  templateUrl: './editar-empresa.component.html',
  styleUrl: './editar-empresa.component.scss',
})
export class EditarEmpresaComponent implements OnInit {
  editarForm: FormGroup;
  idEmpresa: number = 0;
  nombreEmpresa: string = '';
  descripcion: string = '';
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private gestionEmpresaService: GestionEmpresaService
  ) {
    this.editarForm = new FormGroup({
      nombre: this.formBuilder.control('', []),
      descripcion: this.formBuilder.control('', []),
    });
  }

  ngOnInit(): void {
    this.obtenerParametros();
  }

  obtenerParametros(): void {
    this.route.params.subscribe((params) => {
      this.idEmpresa = +params['idEmpresa'];
      this.nombreEmpresa = params['nombreEmpresa'];
      this.descripcion = params['descripcionEmpresa'];

      if (this.idEmpresa && this.nombreEmpresa && this.descripcion) {
        this.editarForm.patchValue({
          nombre: this.nombreEmpresa,
          descripcion: this.descripcion,
        });
      } else {
        this.mostrarPopup('Parámetros inválidos para editar la categoría.', 'error');
        this.regresar();
      }
    });
  }

  onSubmit() {
    if (this.editarForm.valid) {
      const nombre = this.editarForm.get('nombre')?.value;
      const descripcion = this.editarForm.get('descripcion')?.value;

      const data: EditarEmpresaRequestDTO = {
        idEmpresa: this.idEmpresa,
        nombreEmpresa: nombre,
        descripcion: descripcion,
      };

      this.gestionEmpresaService.editarEmpresa(data).subscribe({
        next: () => {
          this.mostrarPopup('Empresa editada exitosamente.', 'success');
          this.regresar();
        },
        error: (error) => {
          this.mostrarPopup('Error al editar la empresa', 'error');
          console.error('Error al editar la empresa:', error);
        },
      });
    } else {
      this.mostrarPopup('Por favor, complete todos los campos del formulario.', 'error');
    }
  }

  regresar() {
    this.router.navigate(['/user-admin/gestionar-empresas']);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
