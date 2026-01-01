package com.api_videojuego.dto.gamer.grupo;

public class GrupoRequestDTO {
	private Integer idCreador;
	private String nombreGrupo;

	public Integer getIdCreador() {
		return idCreador;
	}

	public void setIdCreador(Integer idCreador) {
		this.idCreador = idCreador;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public boolean isValid() {
		return idCreador != null && nombreGrupo != null && !nombreGrupo.isBlank();
	}

}
