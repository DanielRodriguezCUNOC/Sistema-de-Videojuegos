import { Component } from '@angular/core';
import { NavbarComponent } from '../../../../components/share/navbar.component/navbar.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-crear-usuario-gamer-page.component',
  imports: [NavbarComponent, RouterOutlet],
  templateUrl: './crear-usuario-gamer-page.component.html',
  styleUrl: './crear-usuario-gamer-page.component.scss',
})
export class CrearUsuarioGamerPageComponent {}
