export interface ComentarioResponseDTO {
  id: number;
  idUsuario: number;
  nombreUsuario: string;
  comentario: string;
  fechaComentario: Date;
  idComentarioPadre?: number;
  respuestas?: ComentarioResponseDTO[];
}
