import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CarteraDigitalService } from '../../../../services/gamer/cartera/CarteraDigital.service';
import { MasterLoginService } from '../../../../services/login/masterlogin.service';

@Component({
  selector: 'app-cartera-digital.component',
  imports: [CommonModule],
  templateUrl: './cartera-digital.component.html',
  styleUrl: './cartera-digital.component.scss',
})
export class CarteraDigitalComponent implements OnInit {
  saldo: number = 0.0;
  fechaUltimaRecarga: Date = new Date();

  constructor(
    private router: Router,
    private service: CarteraDigitalService,
    private masterLogin: MasterLoginService
  ) {}

  ngOnInit(): void {
    this.cargarSaldo();
  }

  cargarSaldo(): void {
    const idUsuario = this.masterLogin.getUserId();
    this.service.obtenerSaldo(idUsuario).subscribe({
      next: (saldo) => {
        this.saldo = saldo;
      },
      error: (error) => {
        console.error('Error al obtener el saldo:', error);
      },
    });
  }

  recargarSaldo(): void {
    this.router.navigate([`/user-gamer/recargar-cartera/${this.saldo}`]);
  }

  regresar(): void {
    this.router.navigate(['/user-gamer']);
  }
}
