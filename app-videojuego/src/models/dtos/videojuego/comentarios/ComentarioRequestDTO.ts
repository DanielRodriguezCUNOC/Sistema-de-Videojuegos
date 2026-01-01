export interface ComentarioRequestDTO {
  idVideojuego: number;
  idUsuario: number;
  idComentarioPadre: number | null;
  comentario: string;
}
