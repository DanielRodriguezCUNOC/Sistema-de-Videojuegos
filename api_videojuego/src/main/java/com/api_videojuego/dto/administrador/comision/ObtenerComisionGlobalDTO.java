package com.api_videojuego.dto.administrador.comision;

public class ObtenerComisionGlobalDTO {
	private Double comisionGlobal;

	public ObtenerComisionGlobalDTO() {
	}

	public ObtenerComisionGlobalDTO(Double comisionGlobal) {
		this.comisionGlobal = comisionGlobal;
	}

	public Double getComisionGlobal() {
		return comisionGlobal;
	}

	public void setComisionGlobal(Double comisionGlobal) {
		this.comisionGlobal = comisionGlobal;
	}
}