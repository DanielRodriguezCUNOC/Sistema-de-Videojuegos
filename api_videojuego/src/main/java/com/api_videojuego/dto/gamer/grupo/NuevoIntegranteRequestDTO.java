package com.api_videojuego.dto.gamer.grupo;

public class NuevoIntegranteRequestDTO {
	private Integer idGrupo;
	private String nickname;

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isValid() {
		return idGrupo != null && nickname != null && !nickname.isBlank();
	}

}
