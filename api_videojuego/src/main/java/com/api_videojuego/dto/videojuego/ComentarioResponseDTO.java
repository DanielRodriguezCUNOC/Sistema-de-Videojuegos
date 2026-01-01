package com.api_videojuego.dto.videojuego;

import java.sql.Date;

public class ComentarioResponseDTO {
	private Integer idComentario;
	private Integer idComentarioPadre;
	private String nickname;
	private String comentario;
	private Date fechaComentario;

	public ComentarioResponseDTO() {
	}

	public ComentarioResponseDTO(String nickname, String comentario,
			Integer idComentario, Integer idComentarioPadre, Date fechaComentario) {
		this.nickname = nickname;
		this.comentario = comentario;
		this.idComentario = idComentario;
		this.idComentarioPadre = idComentarioPadre;
		this.fechaComentario = fechaComentario;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(Integer idComentario) {
		this.idComentario = idComentario;
	}

	public Integer getIdComentarioPadre() {
		return idComentarioPadre;
	}

	public void setIdComentarioPadre(Integer idComentarioPadre) {
		this.idComentarioPadre = idComentarioPadre;
	}

	public Date getFechaComentario() {
		return fechaComentario;
	}

	public void setFechaComentario(Date fechaComentario) {
		this.fechaComentario = fechaComentario;
	}
}
