export interface ComentarioResponseDTO {
  idComentario: number;
  idComentarioPadre?: number;
  nickname: string;
  comentario: string;
  fechaComentario: Date;
}
