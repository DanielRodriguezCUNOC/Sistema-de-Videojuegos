package com.api_videojuego.dto.gamer.grupo;

public class IntegranteGrupoDTO {

	private Integer idIntegrante;
	private String tipoIntegrante;
	private String nickname;

	public Integer getIdIntegrante() {
		return idIntegrante;
	}

	public void setIdIntegrante(Integer idIntegrante) {
		this.idIntegrante = idIntegrante;
	}

	public String getTipoIntegrante() {
		return tipoIntegrante;
	}

	public void setTipoIntegrante(String tipoIntegrante) {
		this.tipoIntegrante = tipoIntegrante;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
