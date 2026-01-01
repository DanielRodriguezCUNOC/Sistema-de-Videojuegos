package com.api_videojuego.dto.videojuego;

public class ComentarioRequestDTO {
	private Integer idVideojuego;
	private Integer idUsuario;
	private Integer idComentarioPadre;
	private String comentario;

	public Integer getIdComentarioPadre() {
		return idComentarioPadre;
	}

	public void setIdComentarioPadre(Integer idComentarioPadre) {
		this.idComentarioPadre = idComentarioPadre;
	}

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public boolean isValid() {
		return idVideojuego != null && idUsuario != null && comentario != null
				&& !comentario.trim().isEmpty();
	}
}
