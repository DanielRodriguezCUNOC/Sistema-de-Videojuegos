import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../../../components/share/navbar.component/navbar.component';

@Component({
  selector: 'app-crear-usuario-page.component',
  imports: [RouterModule, NavbarComponent],
  templateUrl: './crear-usuario-page.component.html',
  styleUrl: './crear-usuario-page.component.scss',
})
export class CrearUsuarioPageComponent {}
