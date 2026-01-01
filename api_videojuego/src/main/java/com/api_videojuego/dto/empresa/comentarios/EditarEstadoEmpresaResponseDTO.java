package com.api_videojuego.dto.empresa.comentarios;

public class EditarEstadoEmpresaResponseDTO {
	private Integer idEmpresa;
	private String nombreEmpresa;
	private boolean estadoComentarioGeneral;

	public EditarEstadoEmpresaResponseDTO(Integer idEmpresa, String nombreEmpresa,
			boolean estadoComentarioGeneral) {
		this.idEmpresa = idEmpresa;
		this.nombreEmpresa = nombreEmpresa;
		this.estadoComentarioGeneral = estadoComentarioGeneral;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public boolean isEstadoComentarioGeneral() {
		return estadoComentarioGeneral;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public void setEstadoComentarioGeneral(boolean estadoComentarioGeneral) {
		this.estadoComentarioGeneral = estadoComentarioGeneral;
	}

}