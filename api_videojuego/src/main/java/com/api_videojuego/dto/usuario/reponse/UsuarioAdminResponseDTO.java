package com.api_videojuego.dto.usuario.reponse;

public class UsuarioAdminResponseDTO {

	private String nombreCompleto;
	private byte[] avatar;

	// * Getters and Setters */

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

}
