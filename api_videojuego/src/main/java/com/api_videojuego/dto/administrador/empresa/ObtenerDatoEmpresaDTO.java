package com.api_videojuego.dto.administrador.empresa;

public class ObtenerDatoEmpresaDTO {
	private Integer idEmpresa;
	private String nombreEmpresa;
	private String descripcion;
	private String estado;

	public ObtenerDatoEmpresaDTO() {
	}

	public ObtenerDatoEmpresaDTO(Integer idEmpresa, String nombreEmpresa,
			String descripcion, String estado) {
		this.idEmpresa = idEmpresa;
		this.nombreEmpresa = nombreEmpresa;
		this.descripcion = descripcion;
		this.estado = estado;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
