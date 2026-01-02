package com.api_videojuego.dto.gamer.grupo;

public class EditarGrupoRequestDTO {

	private Integer idGrupo;
	private String nuevoNombreGrupo;

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNuevoNombreGrupo() {
		return nuevoNombreGrupo;
	}

	public void setNuevoNombreGrupo(String nuevoNombreGrupo) {
		this.nuevoNombreGrupo = nuevoNombreGrupo;
	}

	public boolean isValid() {
		return idGrupo != null && nuevoNombreGrupo != null
				&& !nuevoNombreGrupo.isBlank();
	}

}
