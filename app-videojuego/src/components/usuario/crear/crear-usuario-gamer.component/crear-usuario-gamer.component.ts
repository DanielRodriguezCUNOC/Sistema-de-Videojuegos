import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { CrearUsuarioGamerDTO } from '../../../../models/dtos/usuario/crear/crear-usuario-gamer';
import { CrearUsuarioService } from '../../../../services/user/crear-usuario.service';

@Component({
  selector: 'app-crear-usuario-gamer',
  imports: [ReactiveFormsModule, SharePopupComponent],
  templateUrl: './crear-usuario-gamer.component.html',
  styleUrl: './crear-usuario-gamer.component.scss',
})
export class CrearUsuarioGamerComponent {
  nuevoRegistroUsuario!: FormGroup;
  selectedFile: File | null = null;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(private formBuilder: FormBuilder, private crearUsuarioService: CrearUsuarioService) {}

  ngOnInit(): void {
    this.nuevoRegistroUsuario = this.formBuilder.group({
      correoUsuario: [null, [Validators.required]],
      nickname: [null, [Validators.required]],
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
      const datosUsuario: CrearUsuarioGamerDTO = {
        correoUsuario: this.nuevoRegistroUsuario.value.correoUsuario,
        nickname: this.nuevoRegistroUsuario.value.nickname,
        password: this.nuevoRegistroUsuario.value.password,
        fechaNacimiento: new Date(this.nuevoRegistroUsuario.value.fechaNacimiento),
        numeroTelefonico: this.nuevoRegistroUsuario.value.numeroTelefonico,
        pais: this.nuevoRegistroUsuario.value.pais,
        avatar: this.selectedFile,
      };

      this.crearUsuarioService.crearUsuarioGamer(datosUsuario).subscribe({
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
