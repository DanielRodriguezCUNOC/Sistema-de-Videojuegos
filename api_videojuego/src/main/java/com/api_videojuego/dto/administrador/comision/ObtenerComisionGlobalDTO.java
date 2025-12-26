package com.api_videojuego.dto.administrador.comision;

import java.math.BigDecimal;
import java.util.Date;

public class ObtenerComisionGlobalDTO {
	private Integer id;
	private BigDecimal comision;
	private Date fechaCreacion;

	// * Getters and Setters */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getComision() {
		return comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}