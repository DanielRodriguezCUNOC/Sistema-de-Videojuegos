import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestionar-comision',
  imports: [CommonModule],
  templateUrl: './gestionar-comision.component.html',
  styleUrl: './gestionar-comision.component.scss',
})
export class GestionarComisionComponent {
  constructor(private router: Router) {}

  gestionarComisionGlobal(): void {
    this.router.navigate(['/user-admin/comision-global']);
  }

  gestionarComisionEspecifica(): void {
    this.router.navigate(['/user-admin/comision-especifica']);
  }

  regresar(): void {
    this.router.navigate(['/user-admin']);
  }
}
