import { Component, OnInit } from '@angular/core';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { DatePipe } from '@angular/common';
import { ComisionEspecificaDTO } from '../../../../models/dtos/comision/comision-especifica-dto';
import { ComisionService } from '../../../../services/admin/comision/comision.service';
import { ListaComisionEspecificaDTO } from '../../../../models/dtos/comision/lista-comision-especifica-dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-gestionar-comision-especifica.component',
  imports: [SharePopupComponent, DatePipe],
  templateUrl: './gestionar-comision-especifica.component.html',
  styleUrl: './gestionar-comision-especifica.component.scss',
})
export class GestionarComisionEspecificaComponent implements OnInit {
  comisiones: ComisionEspecificaDTO[] = [];
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;

  constructor(private service: ComisionService, private router: Router) {}

  ngOnInit(): void {
    this.cargarComisiones();
  }

  cargarComisiones(): void {
    this.service.obtenerComisionesEspecificas().subscribe({
      next: (data: ListaComisionEspecificaDTO) => {
        this.comisiones = data.comisiones;
      },
      error: (err) => {
        this.mostrarPopup('Error al cargar las comisiones específicas.', 'error');
        this.popupMostrar = true;
      },
    });
  }

  regresar(): void {
    this.router.navigate(['/user-admin/gestionar-comisiones']);
  }

  crearComision(): void {
    this.router.navigate(['/user-admin/crear-comision-especifica']);
  }

  editarComisionEspecifica(idEmpresa: number, nombreEmpresa: string, comision: number): void {
    this.router.navigate([
      '/user-admin/editar-comision-especifica',
      idEmpresa,
      nombreEmpresa,
      comision,
    ]);
  }

  eliminarComisionEspecifica(idEmpresa: number): void {
    this.service.eliminarComisionEspecifica(idEmpresa).subscribe({
      next: () => {
        this.mostrarPopup('Comisión específica eliminada correctamente.', 'success');
        this.cargarComisiones();
      },
      error: (err) => {
        this.mostrarPopup('Error al eliminar la comisión específica.', 'error');
        this.popupMostrar = true;
      },
    });
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
