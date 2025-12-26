import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { CrearEmpresaDto } from '../../../../models/dtos/administrador/empresa/crear-empresa-dto';
import { CrearEmpresaService } from '../../../../services/admin/empresa/crear-empresa.service';

@Component({
  selector: 'app-crear-empresa',
  imports: [CommonModule, ReactiveFormsModule, SharePopupComponent],
  templateUrl: './crear-empresa.component.html',
  styleUrl: './crear-empresa.component.scss',
})
export class CrearEmpresaComponent implements OnInit {
  empresaForm!: FormGroup;
  selectedFile: File | null = null;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(
    private fb: FormBuilder,
    private empresaService: CrearEmpresaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.empresaForm = this.fb.group({
      nombreEmpresa: [''],
      descripcion: [''],
      estadoComentario: [false],
      correoUsuario: [''],
      nombreCompleto: [''],
      password: [''],
      fechaNacimiento: [''],
      numeroTelefonico: [''],
      pais: [''],
    });
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  onSubmit(): void {
    if (this.empresaForm.valid) {
      const empresa: CrearEmpresaDto = {
        nombreEmpresa: this.empresaForm.value.nombreEmpresa,
        descripcion: this.empresaForm.value.descripcion,
        estadoComentario: this.empresaForm.value.estadoComentario,
        correoUsuario: this.empresaForm.value.correoUsuario,
        nombreCompleto: this.empresaForm.value.nombreCompleto,
        password: this.empresaForm.value.password,
        fechaNacimiento: new Date(this.empresaForm.value.fechaNacimiento),
        numeroTelefonico: this.empresaForm.value.numeroTelefonico,
        pais: this.empresaForm.value.pais,
        avatar: this.selectedFile,
      };
      this.empresaService.crearEmpresa(empresa).subscribe({
        next: () => {
          this.mostrarPopup('Empresa creada exitosamente', 'success');
          this.router.navigate(['/admin/empresas']);
        },
        error: (error) => {
          console.error('Error al crear empresa', error);
          this.mostrarPopup('Error al crear empresa', 'error');
        },
      });
    }
  }

  regresar(): void {
    this.router.navigate(['/user-admin']);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
