import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CrearUsuarioService } from '../../../../services/user/crear-usuario.service';
import { CrearUsuarioAdminDTO } from '../../../../models/dtos/usuario/crear/crear-usuario-admin';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';

@Component({
  selector: 'app-crear-usuario-admin.component',
  imports: [ReactiveFormsModule, SharePopupComponent],
  templateUrl: './crear-usuario-admin.component.html',
  styleUrl: './crear-usuario-admin.component.scss',
})
export class CrearUsuarioAdminComponent implements OnInit {
  nuevoRegistroUsuario!: FormGroup;
  selectedFile: File | null = null;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(private formBuilder: FormBuilder, private crearUsuarioService: CrearUsuarioService) {}

  ngOnInit(): void {
    this.nuevoRegistroUsuario = this.formBuilder.group({
      correoUsuario: [null, [Validators.required]],
      nombreCompleto: [null, [Validators.required]],
      password: [null, [Validators.required]],
      fechaNacimiento: [null, [Validators.required]],
      numeroTelefonico: [null, [Validators.required]],
      pais: [null, [Validators.required]],
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
      const datosUsuario: CrearUsuarioAdminDTO = {
        correoUsuario: this.nuevoRegistroUsuario.value.correoUsuario,
        nombreCompleto: this.nuevoRegistroUsuario.value.nombreCompleto,
        password: this.nuevoRegistroUsuario.value.password,
        fechaNacimiento: new Date(this.nuevoRegistroUsuario.value.fechaNacimiento),
        numeroTelefonico: this.nuevoRegistroUsuario.value.numeroTelefonico,
        pais: this.nuevoRegistroUsuario.value.pais,
        avatar: this.selectedFile,
      };

      this.crearUsuarioService.crearUsuarioAdmin(datosUsuario).subscribe({
        next: (response) => {
          this.infoMessage = 'Usuario registrado correctamente';
          this.popupTipo = 'success';
          this.popupMostrar = true;
          this.nuevoRegistroUsuario.reset();
          this.selectedFile = null;
        },
        error: (error) => {
          this.infoMessage = error.error.mensaje || 'Error al registrar el usuario';
          this.popupTipo = 'error';
          this.popupMostrar = true;
        },
      });
    }
  }
}
