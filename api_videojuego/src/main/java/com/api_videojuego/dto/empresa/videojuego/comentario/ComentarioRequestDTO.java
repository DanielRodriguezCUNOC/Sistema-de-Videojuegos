package com.api_videojuego.dto.empresa.videojuego.comentario;

import java.time.LocalDate;

import jakarta.ws.rs.FormParam;

public class ComentarioRequestDTO {

	@FormParam("idUsuario")
	private Integer idUsuario;
	@FormParam("idVideojuego")
	private Integer idVideojuego;
	@FormParam("comentario")
	private String comentario;
	@FormParam("comentarioId")
	private Integer comentarioId;
	@FormParam("fechaComentario")
	private LocalDate fechaComentario;

	public ComentarioRequestDTO() {
	}

	// * Getters y Setters */

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getComentarioId() {
		return comentarioId;
	}

	public void setComentarioId(Integer comentarioId) {
		this.comentarioId = comentarioId;
	}

	public LocalDate getFechaComentario() {
		return fechaComentario;
	}

	public void setFechaComentario(LocalDate fechaComentario) {
		this.fechaComentario = fechaComentario;
	}

	public boolean esComentarioValido() {
		return comentario != null && !comentario.trim().isEmpty()
				&& idUsuario != null && idVideojuego != null && fechaComentario != null;
	}
}
