import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UsuarioEmpresaResponseDTO } from '../../../../models/dtos/usuario/response/usuario-empresa-response-dto-';
import { MasterLoginService } from '../../../../services/login/masterlogin.service';
import { RedireccionarService } from '../../../../services/login/redireccionar.service';
import { UsuarioResponseService } from '../../../../services/user/usuario-response.service';

@Component({
  selector: 'app-navbar-usuario-empresa',
  imports: [],
  templateUrl: './navbar-usuario-empresa.component.html',
  styleUrl: './navbar-usuario-empresa.component.scss',
})
export class NavbarUsuarioEmpresaComponent implements OnInit {
  nombreCompleto: string = '';
  nombreEmpresa: string = '';
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
    const userId = this.masterLoginService.getUserId();

    if (!userId) {
      console.log('NavbarUsuarioEmpresa: No hay userId disponible');
      return;
    }

    this.usuarioResponseService.obtenerUsuarioEmpresaResponse(userId).subscribe({
      next: (usuario: UsuarioEmpresaResponseDTO) => {
        this.nombreCompleto = usuario.nombreCompleto;
        this.nombreEmpresa = usuario.nombreEmpresa;
        this.avatarUrl = this.createImageDataUrl(usuario.avatar);
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('NavbarUsuarioEmpresa: Error al obtener datos del usuario:', error);
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
