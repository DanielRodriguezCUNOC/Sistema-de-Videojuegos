import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestionar-grupos.component',
  imports: [CommonModule],
  templateUrl: './gestionar-grupos.component.html',
  styleUrl: './gestionar-grupos.component.scss',
})
export class GestionarGruposComponent {
  constructor(private router: Router) {}

  misGrupos(): void {
    this.router.navigate(['/user-gamer/mis-grupos']);
  }

  gruposQuePertenezco(): void {
    this.router.navigate(['/user-gamer/grupos-pertenezco']);
  }

  regresar(): void {
    this.router.navigate(['/user-gamer']);
  }
}
