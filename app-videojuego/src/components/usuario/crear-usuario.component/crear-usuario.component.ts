import { Component, OnInit } from '@angular/core';
import { SharePopupComponent } from '../../../shared/share-popup.component/share-popup.component';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CrearUsuarioDTO } from '../../../models/dtos/usuario/crear-usuario-dto';
import { CrearUsuarioService } from '../../../services/user/crear-usuario.service';

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

  constructor(private formBuilder: FormBuilder, private crearUsuarioService: CrearUsuarioService) {}

  ngOnInit(): void {
    this.nuevoRegistroUsuario = this.formBuilder.group({
      correo_usuario: [null, [Validators.required]],
      nickname: [null, [Validators.required]],
      password: [null, [Validators.required]],
      fecha_nacimiento: [null, [Validators.required]],
      numero_telefonico: [null, [Validators.required]],
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
      const datosUsuario: CrearUsuarioDTO = {
        correo_usuario: this.nuevoRegistroUsuario.value.email,
        nickname: this.nuevoRegistroUsuario.value.usuario,
        password: this.nuevoRegistroUsuario.value.password,
        fecha_nacimiento: new Date(this.nuevoRegistroUsuario.value.fecha_nacimiento),
        numero_telefonico: this.nuevoRegistroUsuario.value.telefono,
        pais: this.nuevoRegistroUsuario.value.pais,
        avatar: this.selectedFile || null,
      };

      this.crearUsuarioService.crearUsuario(datosUsuario).subscribe({
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
