package com.api_videojuego.dto.administrador.comision;

import java.math.BigDecimal;
import java.util.Date;

public class ComisionEspecificaResponseDTO {
	private Integer idEmpresa;
	private String nombreEmpresa;
	private BigDecimal comision;
	private Date fechaActualizacion;

	public ComisionEspecificaResponseDTO() {
	}

	public ComisionEspecificaResponseDTO(Integer idEmpresa, String nombreEmpresa,
			BigDecimal comision, Date fechaActualizacion) {
		this.idEmpresa = idEmpresa;
		this.nombreEmpresa = nombreEmpresa;
		this.comision = comision;
		this.fechaActualizacion = fechaActualizacion;
	}

	// * Getters and Setters */

	public Integer getId() {
		return idEmpresa;
	}

	public void setId(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public BigDecimal getComisionEspecifica() {
		return comision;
	}

	public void setComisionEspecifica(BigDecimal comision) {
		this.comision = comision;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

}
