package com.api_videojuego.dto.administrador.comision;

import java.math.BigDecimal;

import jakarta.ws.rs.FormParam;

public class EditarComisionEspecificaDTO {
	@FormParam("id")
	private Integer id;
	@FormParam("comisionEspecifica")
	private BigDecimal comisionEspecifica;

	// * Getters and Setters */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getComisionEspecifica() {
		return comisionEspecifica;
	}

	public void setComisionEspecifica(BigDecimal comisionEspecifica) {
		this.comisionEspecifica = comisionEspecifica;
	}

	public boolean esComisionValida() {
		return this.comisionEspecifica != null
				&& this.comisionEspecifica.compareTo(BigDecimal.ZERO) >= 0
				&& this.id != null && this.id > 0;
	}

}
