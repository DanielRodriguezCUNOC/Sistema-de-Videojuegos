package com.api_videojuego.dto.administrador.empresa;

public class EditarEmpresaRequetDTO {
	private String idEmpresa;
	private String nombreEmpresa;
	private String descripcion;

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isValid() {
		return idEmpresa != null && !idEmpresa.isBlank() && nombreEmpresa != null
				&& !nombreEmpresa.isBlank() && descripcion != null
				&& !descripcion.isBlank();
	}
}
