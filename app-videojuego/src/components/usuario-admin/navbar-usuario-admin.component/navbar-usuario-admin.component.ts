import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MasterLoginService } from '../../../services/login/masterlogin.service';
import { RedireccionarService } from '../../../services/login/redireccionar.service';
import { Subscription } from 'rxjs';
import { UsuarioResponseService } from '../../../services/user/usuario-response.service';
import { UsuarioAdministradorResponseDTO } from '../../../models/dtos/usuario/response/usuario-administrador-response-dto';

@Component({
  selector: 'app-navbar-usuario-admin',
  imports: [CommonModule],
  templateUrl: './navbar-usuario-admin.component.html',
  styleUrl: './navbar-usuario-admin.component.scss',
})
export class NavbarUsuarioAdminComponent implements OnInit {
  nombreCompleto: string = '';
  avatarUrl: string = '';

  private subscripcion?: Subscription;

  constructor(
    private masterLoginService: MasterLoginService,
    private redireccionarService: RedireccionarService,
    private usuarioResponseService: UsuarioResponseService
  ) {}

  ngOnInit() {
    this.cargarDatosUsuario();
    this.subscribirseACambiosUsuario();
  }

  logout(): void {
    this.masterLoginService.setLogout();
    this.redireccionarService.redirectToHome();
  }

  /*
   * Carga los datos del usuario actual
   */
  private cargarDatosUsuario(): void {
    this.usuarioResponseService
      .obtenerUsuarioAdminResponse(this.masterLoginService.getUserId())
      .subscribe((usuario: UsuarioAdministradorResponseDTO) => {
        this.nombreCompleto = usuario.nombreCompleto;

        this.avatarUrl = usuario.avatar ? this.createImageDataUrl(usuario.avatar) : '';
      });
  }

  /*
   * Crea una data URL detectando automáticamente si la imagen es PNG o JPEG
   */
  private createImageDataUrl(base64Data: string): string {
    const imageType = this.detectImageType(base64Data);
    return `data:image/${imageType};base64,${base64Data}`;
  }

  /*
   * Detecta el tipo de imagen (png o jpeg) basado en la firma del base64
   */
  private detectImageType(base64Data: string): string {
    if (base64Data.startsWith('/9j/') || base64Data.startsWith('/9k/')) {
      return 'jpeg';
    } else if (base64Data.startsWith('iVBORw0KGgo')) {
      return 'png';
    }
    //* Se coloca png si no se detecta
    return 'png';
  }

  private subscribirseACambiosUsuario(): void {
    this.subscripcion = this.masterLoginService.currentUser$.subscribe((user) => {
      if (user) {
        this.cargarDatosUsuario();
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subscripcion) {
      this.subscripcion.unsubscribe();
    }
  }
}
