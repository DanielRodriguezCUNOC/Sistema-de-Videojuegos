package com.api_videojuego.dto.usuario.reponse;

public class UsuarioGamerResponseDTO {

	private String nickname;
	private String avatar;

	public UsuarioGamerResponseDTO(String nickname, String avatar) {
		this.nickname = nickname;
		this.avatar = avatar;
	}

	// * Getters and Setters */

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}