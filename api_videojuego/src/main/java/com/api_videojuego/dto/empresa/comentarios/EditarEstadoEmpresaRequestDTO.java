package com.api_videojuego.dto.empresa.comentarios;

public class EditarEstadoEmpresaRequestDTO {

	private Integer idEmpresa;
	private boolean estadoComentarios;

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public boolean getEstadoComentarios() {
		return estadoComentarios;
	}

	public void setEstadoComentarios(boolean estadoComentarios) {
		this.estadoComentarios = estadoComentarios;
	}

	public boolean isValido() {
		return idEmpresa != null;
	}
}