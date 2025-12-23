package com.api_videojuego.dto.administrador.comision;

import java.math.BigDecimal;

public class ComisionEspecificaRequestDTO {

	private String nombreEmpresa;
	private BigDecimal comisionEspecifica;

	// * Getters and Setters */

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public BigDecimal getComisionEspecifica() {
		return comisionEspecifica;
	}

	public void setComisionEspecifica(BigDecimal comisionEspecifica) {
		this.comisionEspecifica = comisionEspecifica;
	}

	public boolean esComisionValida() {
		return comisionEspecifica != null
				&& comisionEspecifica.compareTo(BigDecimal.ZERO) >= 0
				&& nombreEmpresa != null && !nombreEmpresa.isBlank();
	}

}
