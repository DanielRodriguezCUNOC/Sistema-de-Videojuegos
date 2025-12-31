import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MasterLoginService } from '../../../services/login/masterlogin.service';
import { RedireccionarService } from '../../../services/login/redireccionar.service';
import { UsuarioResponseService } from '../../../services/user/usuario-response.service';
import { UsuarioGamerResponseDTO } from '../../../models/dtos/usuario/response/usuario-gamer-response-dto';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar-usuario',
  imports: [RouterLink, CommonModule],
  templateUrl: './navbar-usuario.component.html',
  styleUrl: './navbar-usuario.component.scss',
})
export class NavbarUsuarioComponent implements OnInit, OnDestroy {
  avatarUrl: string = '';
  nickname = '';

  private subscripcion?: Subscription;
  @Output() enviarNickname = new EventEmitter<string>();

  constructor(
    private masterLoginService: MasterLoginService,
    private redireccionarService: RedireccionarService,
    private usuarioResponseService: UsuarioResponseService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.cargarDatosUsuario();
  }

  /*
   * Carga los datos del usuario actual
   */
  private cargarDatosUsuario(): void {
    const userId = this.masterLoginService.getUserId();

    if (!userId) {
      console.log('NavbarUsuario: No hay userId disponible');
      return;
    }

    this.usuarioResponseService.obtenerUsuarioGamerResponse(userId).subscribe({
      next: (usuario: UsuarioGamerResponseDTO) => {
        this.nickname = usuario.nickname;
        this.avatarUrl = this.createImageDataUrl(usuario.avatar);
        this.cdr.detectChanges();
        this.enviarNickname.emit(this.nickname);
      },
      error: (error) => {
        console.error('NavbarUsuario: Error al obtener datos del usuario:', error);
      },
    });
  }

  logout(): void {
    this.masterLoginService.setLogout();
    this.redireccionarService.redirectToHome();
  }

  /*
   * Crea una data URL detectando automáticamente si la imagen es PNG o JPEG
   */
  private createImageDataUrl(base64Data: string): string {
    const imageType = this.detectImageType(base64Data);
    return `data:image/${imageType};base64,${base64Data}`;
  }

  /*
   * Detecta el tipo de imagen (png o jpeg) basado en el MIME
   */
  private detectImageType(base64Data: string): string {
    if (base64Data.startsWith('/9j/') || base64Data.startsWith('/9k/')) {
      return 'jpeg';
    } else if (base64Data.startsWith('iVBORw0KGgo')) {
      return 'png';
    }
    return 'png';
  }

  carteraDigital(): void {
    this.router.navigate(['/user-gamer/cartera-digital']);
  }

  tiendaVideojuegos(): void {
    this.router.navigate(['/user-gamer/tienda']);
  }

  ngOnDestroy(): void {
    if (this.subscripcion) {
      this.subscripcion.unsubscribe();
    }
  }
}
