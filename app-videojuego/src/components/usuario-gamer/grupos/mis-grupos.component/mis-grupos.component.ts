import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { GrupoRequestDto } from '../../../../models/dtos/gamer/grupo/grupo-request-dto';
import { GrupoResponseDto } from '../../../../models/dtos/gamer/grupo/grupo-reponse-dto';

@Component({
  selector: 'app-mis-grupos.component',
  imports: [SharePopupComponent],
  templateUrl: './mis-grupos.component.html',
  styleUrl: './mis-grupos.component.scss',
})
export class MisGruposComponent implements OnInit {
  grupos: GrupoResponseDto[] = [];
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.cargarGrupos();
  }

  cargarGrupos(): void {}

  regresar(): void {
    this.router.navigate(['/user-gamer/gamer-module']);
  }

  crearGrupo(): void {
    this.router.navigate(['/user-gamer/crear-grupo']);
  }

  administrarGrupo(idGrupo: number): void {
    this.router.navigate(['/user-gamer/administrar-grupo', idGrupo]);
  }

  editarGrupo(idGrupo: number, nombreGrupo: string): void {
    this.router.navigate(['/user-gamer/editar-grupo', idGrupo, nombreGrupo]);
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
