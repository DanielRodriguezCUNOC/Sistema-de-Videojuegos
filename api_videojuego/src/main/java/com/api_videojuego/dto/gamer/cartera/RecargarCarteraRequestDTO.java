package com.api_videojuego.dto.gamer.cartera;

import java.math.BigDecimal;

public class RecargarCarteraRequestDTO {
	private Integer idUsuario;
	private BigDecimal monto;

	public RecargarCarteraRequestDTO() {
	}

	public RecargarCarteraRequestDTO(Integer idUsuario, BigDecimal monto) {
		this.idUsuario = idUsuario;
		this.monto = monto;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public boolean isValid() {
		return idUsuario != null && monto != null
				&& monto.compareTo(BigDecimal.ZERO) > 0;
	}
}
