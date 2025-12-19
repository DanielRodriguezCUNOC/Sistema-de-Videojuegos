import { Component } from '@angular/core';
import { ListaRapidaJuegoCompradoDTO } from '../../../models/dtos/usuario/lista-rapida-juego-comprado-dto';
import { NavbarUsuarioComponent } from '../../usuario/navbar-usuario.component/navbar-usuario.component';
import { FooterComponent } from '../../share/footer.component/footer.component';
import { CurrencyPipe } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-gamer-module',
  imports: [NavbarUsuarioComponent, FooterComponent, CurrencyPipe, RouterModule],
  templateUrl: './gamer-module.component.html',
  styleUrl: './gamer-module.component.scss',
})
export class GamerModuleComponent {
  juegosComprados: ListaRapidaJuegoCompradoDTO[] = [];
  juegosCarousel: ListaRapidaJuegoCompradoDTO[] = [];
  indiceCarrusel = 0;

  get imagenActual(): string {
    return this.juegosCarousel[this.indiceCarrusel].imagenJuego;
  }

  get tituloActual(): string {
    return this.juegosCarousel[this.indiceCarrusel].titulo;
  }

  get juegoActual() {
    return this.juegosCarousel[this.indiceCarrusel];
  }

  anteriorImagen() {
    this.indiceCarrusel =
      (this.indiceCarrusel - 1 + this.juegosCarousel.length) % this.juegosCarousel.length;
  }

  siguienteImagen() {
    this.indiceCarrusel = (this.indiceCarrusel + 1) % this.juegosCarousel.length;
  }

  trackById(index: number, juego: ListaRapidaJuegoCompradoDTO) {
    return juego.idVideojuego;
  }

  totalJuegos = this.juegosComprados.length;
  saldoCartera = 25.5;
}
