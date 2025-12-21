package com.api_videojuego.dto.administrador.comision;

import java.math.BigDecimal;

public class ComisionEspecificaResponseDTO {
	private Integer id;
	private String nombreEmpresa;
	private BigDecimal comisionEspecifica;

	public ComisionEspecificaResponseDTO() {
	}

	public ComisionEspecificaResponseDTO(Integer id, String nombreEmpresa,
			BigDecimal comisionEspecifica) {
		this.id = id;
		this.nombreEmpresa = nombreEmpresa;
		this.comisionEspecifica = comisionEspecifica;
	}

	// * Getters and Setters */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

}
