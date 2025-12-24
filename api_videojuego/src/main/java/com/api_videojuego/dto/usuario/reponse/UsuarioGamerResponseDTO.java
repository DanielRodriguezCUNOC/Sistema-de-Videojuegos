package com.api_videojuego.dto.usuario.reponse;

public class UsuarioGamerResponseDTO {

	private String nickname;
	private byte[] avatar;

	// * Getters and Setters */

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

}