package com.api_videojuego.dto.empresa.comentario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComentarioResponseDTO {

	private Integer idComentario;
	private Integer idUsuario;
	private String nombreUsuario;
	private Integer idVideojuego;
	private String comentario;
	private Integer comentarioId;
	private LocalDate fechaComentario;
	private List<ComentarioResponseDTO> respuestas;

	public ComentarioResponseDTO() {
		this.respuestas = new ArrayList<>();
	}

	// * Getters y Setters*/
	public Integer getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(Integer idComentario) {
		this.idComentario = idComentario;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
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

	public List<ComentarioResponseDTO> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<ComentarioResponseDTO> respuestas) {
		this.respuestas = respuestas;
	}

	public void agregarRespuesta(ComentarioResponseDTO respuesta) {
		if (this.respuestas == null) {
			this.respuestas = new ArrayList<>();
		}
		this.respuestas.add(respuesta);
	}

	public boolean esComentarioPrincipal() {
		return this.comentarioId == null;
	}

}
