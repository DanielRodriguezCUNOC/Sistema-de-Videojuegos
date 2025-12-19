import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestionar-comision-global',
  imports: [CommonModule],
  templateUrl: './gestionar-comision-global.component.html',
  styleUrl: './gestionar-comision-global.component.scss',
})
export class GestionarComisionGlobalComponent implements OnInit {
  comisionGlobal: number = 15;
  fechaActualizacion: string = '15 de Diciembre, 2024';

  constructor(private router: Router) {}

  ngOnInit(): void {}

  editarComision(): void {
    this.router.navigate(['/user-admin/editar-comision-global']);
  }

  regresar(): void {
    this.router.navigate(['/user-admin/gestionar-comisiones']);
  }
}
