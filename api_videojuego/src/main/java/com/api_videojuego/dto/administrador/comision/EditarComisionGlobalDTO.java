package com.api_videojuego.dto.administrador.comision;

import jakarta.ws.rs.FormParam;

public class EditarComisionGlobalDTO {
	@FormParam("comisionGlobal")
	private Double comisionGlobal;

	public Double getComisionGlobal() {
		return comisionGlobal;
	}

	public void setComisionGlobal(Double comisionGlobal) {
		this.comisionGlobal = comisionGlobal;
	}

	public boolean esValida() {
		return comisionGlobal != null && comisionGlobal >= 0
				&& comisionGlobal <= 100;
	}
}
