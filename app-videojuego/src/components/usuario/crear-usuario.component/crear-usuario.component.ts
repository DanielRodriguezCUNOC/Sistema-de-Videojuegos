import { Component, OnInit } from '@angular/core';
import { SharePopupComponent } from '../../../shared/share-popup.component/share-popup.component';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-crear-usuario.component',
  imports: [SharePopupComponent, ReactiveFormsModule],
  templateUrl: './crear-usuario.component.html',
  styleUrl: './crear-usuario.component.scss',
})
export class CrearUsuarioComponent implements OnInit {
  nuevoRegistroUsuario!: FormGroup;
  selectedFile: File | null = null;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.nuevoRegistroUsuario = this.formBuilder.group({
      nombre: [null, [Validators.required]],
      tipoUsuario: ['', [Validators.required]],
      email: [null, [Validators.required]],
      usuario: [null, [Validators.required]],
      password: [null, [Validators.required]],
      telefono: [null, [Validators.required]],
    });
  }

  //* Manejo de la selección de archivo */
  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  //* Envío del formulario */
  submit(): void {
    if (this.nuevoRegistroUsuario.valid) {
      const formData = this.nuevoRegistroUsuario.value;
    }
  }
}
