import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { GrupoResponseDto } from '../../../../models/dtos/gamer/grupo/grupo-reponse-dto';
import { IntegranteGrupoService } from '../../../../services/gamer/grupo/integrante-grupo.service';
import { GrupoService } from '../../../../services/gamer/grupo/grupo.service';
import { MasterLoginService } from '../../../../services/login/masterlogin.service';
import { EditarGrupoRequestDto } from '../../../../models/dtos/gamer/grupo/editar-grupo-request-dto';
import { EditarEstadoGrupoDto } from '../../../../models/dtos/gamer/grupo/editar-estado-grupo-dto';
import { EliminarIntegranteDto } from '../../../../models/dtos/gamer/grupo/eliminar-integrante-dto';

@Component({
  selector: 'app-mis-grupos.component',
  imports: [SharePopupComponent],
  templateUrl: './mis-grupos.component.html',
  styleUrl: './mis-grupos.component.scss',
})
export class MisGruposComponent implements OnInit {
  grupos: GrupoResponseDto[] = [];
  gruposActivos: GrupoResponseDto[] = [];
  gruposInactivos: GrupoResponseDto[] = [];
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(
    private router: Router,
    private integranteGrupoService: IntegranteGrupoService,
    private grupoService: GrupoService,
    private masterLogin: MasterLoginService
  ) {}

  ngOnInit(): void {
    this.cargarGrupos();
  }

  cargarGrupos(): void {
    const idUsuario = this.masterLogin.getUserId()!;

    if (idUsuario === null || idUsuario === undefined) {
      this.mostrarPopup('Usuario no autenticado', 'error');
      return;
    }

    this.integranteGrupoService.obtenerGrupos(idUsuario).subscribe({
      next: (data) => {
        this.grupos = data;
        console.log('Grupos cargados:', this.grupos);
        this.separartGrupos();
      },
      error: (error) => {
        this.mostrarPopup('Error al cargar los grupos', 'error');
        console.error('Error al cargar los grupos:', error);
      },
    });
  }

  regresar(): void {
    this.router.navigate(['/user-gamer/']);
  }

  crearGrupo(): void {
    this.router.navigate(['/user-gamer/crear-grupo']);
  }

  private separartGrupos(): void {
    this.gruposActivos = this.grupos.filter((grupo) => grupo.estado === true);
    this.gruposInactivos = this.grupos.filter((grupo) => grupo.estado === false);
  }

  editarGrupo(idGrupo: number, nombreGrupo: string): void {
    this.router.navigate([`/user-gamer/editar-grupo/${idGrupo}/${nombreGrupo}`]);
  }

  desactivarGrupo(idGrupo: number): void {
    const data: EditarEstadoGrupoDto = {
      idGrupo: idGrupo,
      nuevoEstado: false,
    };

    this.grupoService.cambiarEstadoGrupo(data).subscribe({
      next: () => {
        this.mostrarPopup('Grupo desactivado correctamente', 'success');
        this.cargarGrupos();
      },
      error: (error) => {
        this.mostrarPopup('Error al desactivar el grupo', 'error');
        console.error('Error al desactivar el grupo:', error);
      },
    });
  }

  activarGrupo(idGrupo: number): void {
    const data: EditarEstadoGrupoDto = {
      idGrupo: idGrupo,
      nuevoEstado: true,
    };

    this.grupoService.cambiarEstadoGrupo(data).subscribe({
      next: () => {
        this.mostrarPopup('Grupo activado correctamente', 'success');
        this.cargarGrupos();
      },
      error: (error) => {
        this.mostrarPopup('Error al activar el grupo', 'error');
        console.error('Error al activar el grupo:', error);
      },
    });
  }

  eliminarIntegrante(idGrupo: number, idIntegrante: number): void {
    const data: EliminarIntegranteDto = {
      idGrupo: idGrupo,
      idIntegrante: idIntegrante,
    };

    this.integranteGrupoService.eliminarIntegrante(data).subscribe({
      next: () => {
        this.mostrarPopup('Integrante eliminado correctamente', 'success');
        this.cargarGrupos();
      },
      error: (error) => {
        this.mostrarPopup('Error al eliminar el integrante', 'error');
        console.error('Error al eliminar el integrante:', error);
      },
    });
  }

  agregarIntegrante(idGrupo: number): void {
    this.router.navigate([`/user-gamer/agregar-integrante-grupo/${idGrupo}`]);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
