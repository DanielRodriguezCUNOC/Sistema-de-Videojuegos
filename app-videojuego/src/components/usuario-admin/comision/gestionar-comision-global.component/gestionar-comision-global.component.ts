import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ComisionService } from '../../../../services/admin/comision/comision.service';
import { ObtenerComisionGlobalDTO } from '../../../../models/dtos/comision/obtener-comision-global-dto';

@Component({
  selector: 'app-gestionar-comision-global',
  imports: [CommonModule],
  templateUrl: './gestionar-comision-global.component.html',
  styleUrl: './gestionar-comision-global.component.scss',
})
export class GestionarComisionGlobalComponent implements OnInit {
  comisionId: number = 5;
  comisionGlobal: number = 5;
  fechaActualizacion: Date = new Date('2023-12-15');

  constructor(private router: Router, private comisionService: ComisionService) {}

  ngOnInit(): void {
    this.cargarComisionGlobal();
  }

  cargarComisionGlobal(): void {
    this.comisionService.obtenerComisionGlobal().subscribe({
      next: (data: ObtenerComisionGlobalDTO) => {
        console.log('Comisión global cargada:', data);
        this.comisionId = data.id;
        this.comisionGlobal = data.comision;
        this.fechaActualizacion = data.fechaCreacion;
      },
      error: (err) => {
        console.error('Error al cargar la comisión global:', err);
      },
    });
  }

  editarComision(): void {
    this.router.navigate([
      '/user-admin/editar-comision-global',
      this.comisionGlobal,
      this.comisionId,
    ]);
  }

  regresar(): void {
    this.router.navigate(['/user-admin/gestionar-comisiones']);
  }
}
