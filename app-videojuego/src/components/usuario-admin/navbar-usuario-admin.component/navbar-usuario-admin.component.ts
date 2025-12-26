import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
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
    private usuarioResponseService: UsuarioResponseService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.cargarDatosUsuario();
  }

  logout(): void {
    this.masterLoginService.setLogout();
    this.redireccionarService.redirectToHome();
  }

  /*
   * Carga los datos del usuario actual
   */
  private cargarDatosUsuario(): void {
    const userId = this.masterLoginService.getUserId();

    this.usuarioResponseService.obtenerUsuarioAdminResponse(userId).subscribe({
      next: (usuario: UsuarioAdministradorResponseDTO) => {
        this.nombreCompleto = usuario.nombreCompleto;
        this.avatarUrl = usuario.avatar ? this.createImageDataUrl(usuario.avatar) : '';
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('NavbarUsuarioAdmin: Error al obtener datos del usuario:', error);
      },
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

  ngOnDestroy(): void {
    if (this.subscripcion) {
      this.subscripcion.unsubscribe();
    }
  }
}
