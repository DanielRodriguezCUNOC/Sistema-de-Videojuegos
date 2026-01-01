import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PerfilVideojuegoService } from '../../../services/perfiles/perfil-videojuego.service';
import { SharePopupComponent } from '../../../shared/share-popup.component/share-popup.component';
import { PerfilVideojuegoResponseDTO } from '../../../models/dtos/videojuego/perfil-videojuego-response-dto';
import { ConvertirImagen } from '../../../utils/convertir-imagen';
import { ComprarVideojuegoService } from '../../../services/gamer/compra/ComprarVideojuego.service';
import { ComprarVideojuegoRequestDTO } from '../../../models/dtos/gamer/compra/ComprarVideojuegoRequestDTO';
import { MasterLoginService } from '../../../services/login/masterlogin.service';
import { ComentarioResponseDTO } from '../../../models/dtos/videojuego/comentarios/ComentarioResponseDTO';
import { ComentarioRequestDTO } from '../../../models/dtos/videojuego/comentarios/ComentarioRequestDTO';
import { CalificacionVideojuegoService } from '../../../services/calificacion/calificacion-videojuego.service';
import { CalificacionVideojuegoRequestDTO } from '../../../models/dtos/videojuego/calificacion-videojuego-request-dto';
import { ComentarioService } from '../../../services/comentario/comentario.service';

@Component({
  selector: 'app-perfil-videojuego.component',
  imports: [CommonModule, FormsModule, SharePopupComponent],
  templateUrl: './perfil-videojuego.component.html',
  styleUrl: './perfil-videojuego.component.scss',
})
export class PerfilVideojuegoComponent implements OnInit {
  //* Clase para dar formato a las imágenes */
  private convertirImagen = new ConvertirImagen();
  //* Datos del videojuego */
  perfilVideojuego: PerfilVideojuegoResponseDTO | null = null;
  idVideojuego: number = 0;
  //* Propiedades para el popup */
  infoMessage: string | null = null;
  popupTipo: 'error' | 'success' | 'info' = 'info';
  popupMostrar = false;
  //* Propiedades para comentarios */
  nuevoComentario: string = '';
  comentarios: ComentarioResponseDTO[] = [];
  comentariosOrganizados: ComentarioResponseDTO[] = [];
  respondiendo: number | null = null;
  comentarioRespuesta: string = '';
  //* Propiedades para calificación */
  mostrandoCalificacion: boolean = false;
  calificacionSeleccionada: number = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private perfilVideojuegoService: PerfilVideojuegoService,
    private comprarVideojuegoService: ComprarVideojuegoService,
    private masterLogin: MasterLoginService,
    private comentarioService: ComentarioService,
    private calificacionService: CalificacionVideojuegoService
  ) {}
  ngOnInit(): void {
    this.obtenerParametros();
    this.obtenerPerfilVideojuego();
    this.obtenerComentarios();
  }

  obtenerPerfilVideojuego(): void {
    this.perfilVideojuegoService.obtenerPerfilVideojuego(this.idVideojuego).subscribe({
      next: (data) => {
        this.perfilVideojuego = data;
      },
      error: (error) => {
        this.mostrarPopup('Error al cargar el perfil del videojuego.', 'error');
        console.error('Error al cargar el perfil del videojuego:', error);
        this.regresar();
      },
    });
  }

  obtenerComentarios(): void {
    this.comentarioService.obtenerComentariosPorVideojuego(this.idVideojuego).subscribe({
      next: (data) => {
        this.comentarios = data;
        this.organizarComentarios();
      },
      error: (error) => {
        console.error('Error al cargar los comentarios:', error);
      },
    });
  }

  registrarComentario(): void {
    if (!this.nuevoComentario.trim()) {
      this.mostrarPopup('El comentario no puede estar vacío.', 'error');
      return;
    }

    const data: ComentarioRequestDTO = {
      idVideojuego: Number(this.idVideojuego),
      comentario: this.nuevoComentario.trim(),
      idUsuario: this.masterLogin.getUserId()!,
      idComentarioPadre: null,
    };

    if (data.idUsuario === null) {
      this.mostrarPopup('Debe iniciar sesión para agregar un comentario.', 'error');
      return;
    }

    this.comentarioService.agregarComentario(data).subscribe({
      next: () => {
        this.mostrarPopup('Comentario agregado con éxito.', 'success');
        this.nuevoComentario = '';
        this.obtenerComentarios();
      },
      error: (error) => {
        this.mostrarPopup('Error al agregar el comentario.', 'error');
        console.error('Error al agregar el comentario:', error);
      },
    });
  }

  obtenerParametros(): void {
    this.route.params.subscribe((params) => {
      this.idVideojuego = +params['idVideojuego'];

      if (!this.idVideojuego) {
        this.mostrarPopup('Parámetros inválidos para ver el videojuego.', 'error');
        this.regresar();
      }
    });
  }

  regresar() {
    this.router.navigate(['/user-gamer/tienda']);
  }

  obtenerImagenBase64(imagenBase64: string): string {
    if (!imagenBase64) return '';
    return this.convertirImagen.createImageDataUrl(imagenBase64);
  }

  comprarVideojuego(): void {
    if (!this.idVideojuego) {
      this.mostrarPopup('ID de videojuego inválido.', 'error');
      return;
    }
    const data: ComprarVideojuegoRequestDTO = {
      idVideojuego: this.idVideojuego,
      idUsuario: this.masterLogin.getUserId()!,
      precio: this.perfilVideojuego?.precio || 0,
    };

    if (data.idUsuario === null) {
      this.mostrarPopup('Debe iniciar sesión para comprar un videojuego.', 'error');
      return;
    }

    this.comprarVideojuegoService.comprarVideojuego(data).subscribe({
      next: () => {
        this.mostrarPopup('Compra realizada con éxito.', 'success');
        setTimeout(() => {
          this.regresar();
        }, 2000);
      },
      error: (error) => {
        this.mostrarPopup('Error al realizar la compra del videojuego.', 'error');
        console.error('Error al comprar el videojuego:', error);
      },
    });
  }

  private mostrarPopup(mensaje: string, tipo: 'error' | 'success' | 'info'): void {
    this.infoMessage = mensaje;
    this.popupTipo = tipo;
    this.popupMostrar = true;
  }

  mostrarFormularioCalificacion(): void {
    if (!this.masterLogin.getUserId()) {
      this.mostrarPopup('Debe iniciar sesión para calificar el videojuego.', 'error');
      return;
    }
    this.mostrandoCalificacion = true;
    this.calificacionSeleccionada = 0;
  }

  cerrarFormularioCalificacion(): void {
    this.mostrandoCalificacion = false;
    this.calificacionSeleccionada = 0;
  }

  seleccionarCalificacion(calificacion: number): void {
    this.calificacionSeleccionada = calificacion;
  }

  enviarCalificacion(): void {
    if (this.calificacionSeleccionada < 1 || this.calificacionSeleccionada > 5) {
      this.mostrarPopup('Seleccione una calificación válida (1-5).', 'error');
      return;
    }

    const data: CalificacionVideojuegoRequestDTO = {
      idVideojuego: this.idVideojuego,
      idUsuario: this.masterLogin.getUserId()!,
      calificacion: this.calificacionSeleccionada,
    };

    this.calificacionService.registrarCalificacion(data).subscribe({
      next: () => {
        this.mostrarPopup('Calificación enviada con éxito.', 'success');
        this.cerrarFormularioCalificacion();
        this.obtenerPerfilVideojuego();
      },
      error: (error) => {
        this.mostrarPopup('Error al enviar la calificación.', 'error');
        console.error('Error al calificar videojuego:', error);
      },
    });
  }

  organizarComentarios(): void {
    //* Separar comentarios padre de las respuestas
    const comentariosPadre = this.comentarios.filter((c) => !c.idComentarioPadre);
    const comentariosHijos = this.comentarios.filter((c) => c.idComentarioPadre);

    //* Creamos la estructura organizada para el template
    this.comentariosOrganizados = comentariosPadre.map((padre) => ({
      ...padre,
      //* Agregar las respuestas correspondientes
      respuestas: comentariosHijos.filter((hijo) => hijo.idComentarioPadre === padre.idComentario),
    }));
  }

  iniciarRespuesta(comentarioId: number): void {
    this.respondiendo = comentarioId;
    this.comentarioRespuesta = '';
  }

  cancelarRespuesta(): void {
    this.respondiendo = null;
    this.comentarioRespuesta = '';
  }

  responderComentario(comentarioPadreId: number): void {
    if (!this.comentarioRespuesta.trim()) {
      this.mostrarPopup('La respuesta no puede estar vacía.', 'error');
      return;
    }

    const data: ComentarioRequestDTO = {
      idVideojuego: Number(this.idVideojuego),
      comentario: this.comentarioRespuesta.trim(),
      idUsuario: this.masterLogin.getUserId()!,
      idComentarioPadre: comentarioPadreId,
    };

    if (data.idUsuario === null) {
      this.mostrarPopup('Debe iniciar sesión para responder.', 'error');
      return;
    }

    this.comentarioService.agregarComentario(data).subscribe({
      next: () => {
        this.mostrarPopup('Respuesta agregada con éxito.', 'success');
        this.cancelarRespuesta();
        this.obtenerComentarios();
      },
      error: (error) => {
        this.mostrarPopup('Error al agregar la respuesta.', 'error');
        console.error('Error al agregar la respuesta:', error);
      },
    });
  }
}
