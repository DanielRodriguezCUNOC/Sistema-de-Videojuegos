package com.api_videojuego.dto.usuario.reponse;

public class UsuarioAdminResponseDTO {

	private String nombreCompleto;
	private String avatar;

	public UsuarioAdminResponseDTO(String nombreCompleto, String avatar) {
		this.nombreCompleto = nombreCompleto;
		this.avatar = avatar;
	}

	// * Getters and Setters */

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
