import { Component, OnInit } from '@angular/core';
import { SharePopupComponent } from '../../../../shared/share-popup.component/share-popup.component';
import { DatePipe } from '@angular/common';
import { ComisionEspecificaDTO } from '../../../../models/dtos/comision/comision-especifica-dto';
import { ComisionService } from '../../../../services/admin/comision/comision.service';

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

  constructor(private service: ComisionService) {}

  ngOnInit(): void {}

  cargarComisiones(): void {
    this.service.obtenerComisionesEspecificas().subscribe({
      next: (data) => {
        this.comisiones = data.comisiones;
      },
      error: (err) => {
        this.mostrarPopup('Error al cargar las comisiones específicas.', 'error');
        this.popupMostrar = true;
      },
    });
  }

  regresar(): void {}

  crearComision(): void {}

  editarComisionEmpresa(idEmpresa: number, comision: number): void {}

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }
}
