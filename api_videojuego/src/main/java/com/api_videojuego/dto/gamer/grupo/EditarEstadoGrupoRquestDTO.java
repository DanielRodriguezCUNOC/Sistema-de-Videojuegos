package com.api_videojuego.dto.gamer.grupo;

public class EditarEstadoGrupoRquestDTO {

	private Integer idGrupo;
	private boolean nuevoEstado;

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public boolean isNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(boolean nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}
}
