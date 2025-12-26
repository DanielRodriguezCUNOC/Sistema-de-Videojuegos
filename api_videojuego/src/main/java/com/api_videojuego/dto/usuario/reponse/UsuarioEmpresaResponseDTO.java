package com.api_videojuego.dto.usuario.reponse;

public class UsuarioEmpresaResponseDTO {

	private String nombreCompleto;
	private String nombreEmpresa;
	private String avatar;

	public UsuarioEmpresaResponseDTO(String nombreCompleto, String avatar,
			String nombreEmpresa) {
		this.nombreCompleto = nombreCompleto;
		this.avatar = avatar;
		this.nombreEmpresa = nombreEmpresa;
	}

	// * Getters and Setters */

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
