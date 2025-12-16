import { Component } from '@angular/core';
import { NavbarUsuarioComponent } from '../navbar-usuario.component/navbar-usuario.component';
import { ListaRapidaJuegoCompradoDTO } from '../../../models/dtos/usuario/lista-rapida-juego-comprado-dto';
import { CurrencyPipe } from '@angular/common';
import { RouterLinkActive, RouterModule } from '@angular/router';
import { FooterComponent } from '../../share/footer.component/footer.component';

@Component({
  selector: 'app-modulo-usuario.component',
  imports: [NavbarUsuarioComponent, CurrencyPipe, RouterModule, FooterComponent],
  templateUrl: './modulo-usuario.component.html',
  styleUrl: './modulo-usuario.component.scss',
})
export class ModuloUsuarioComponent {
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
