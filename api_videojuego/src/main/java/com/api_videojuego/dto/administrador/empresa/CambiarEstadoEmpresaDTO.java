package com.api_videojuego.dto.administrador.empresa;

public class CambiarEstadoEmpresaDTO {

	private String idEmpresa;
	private String estado;

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isValid() {
		return idEmpresa != null && !idEmpresa.isBlank() && estado != null
				&& !estado.isBlank() && (estado.toLowerCase().equals("activa")
						|| estado.toLowerCase().equals("inactiva"));
	}
}
