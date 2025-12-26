import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { LoginService } from '../../../services/login/login.service';
import { SharePopupComponent } from '../../../shared/share-popup.component/share-popup.component';
import { RedireccionarService } from '../../../services/login/redireccionar.service';
import { UserResponseLoginDTO } from '../../../models/dtos/login/user-response-login';
import { MasterLoginService } from '../../../services/login/masterlogin.service';

@Component({
  selector: 'app-login.component',
  imports: [ReactiveFormsModule, SharePopupComponent, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  loginForm: FormGroup;
  isLoading: boolean = false;
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(
    private fb: FormBuilder,
    private loginService: LoginService,
    private redireccionarService: RedireccionarService,
    private masterLogin: MasterLoginService
  ) {
    this.loginForm = this.fb.group({
      usuario: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  submit(): void {
    if (this.loginForm.invalid) {
      this.mostrarPopup('Por favor, complete todos los campos del formulario.', 'error');
      return;
    }
    this.isLoading = true;
    this.popupMostrar = false;

    const usuario: FormData = new FormData();
    usuario.append('usuario', this.loginForm.value.usuario);
    usuario.append('password', this.loginForm.value.password);

    this.loginService.autenticacionBackend(usuario).subscribe({
      next: (user: UserResponseLoginDTO) => {
        this.mostrarPopup('Inicio de sesión exitoso', 'success');

        this.masterLogin.setLogin(user);

        //*Redirigir al dashboard del usuario segun su rol
        setTimeout(() => {
          this.redireccionarService.redireccionarSegunRol(user.idRol).catch(() => {
            this.mostrarPopup('Error al redirigir.', 'error');
          });
        }, 1000);
      },
      error: (err) => {
        this.isLoading = false;
        let mensaje = this.obtenerError(err.status);
        this.mostrarPopup(mensaje, 'error');
      },
      complete: () => {
        this.isLoading = false;
      },
    });
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }

  private obtenerError(status: number): string {
    const errorMessages: Record<number, string> = {
      400: 'Datos inválidos. Por favor, verifique la información ingresada.',
      401: 'Credenciales inválidas. Por favor, inténtelo de nuevo.',
      500: 'Error interno del servidor. Por favor, inténtelo más tarde.',
      0: 'Error de conexión. Por favor, verifique su conexión a Internet.',
    };
    return errorMessages[status] || 'Error inesperado';
  }
}
