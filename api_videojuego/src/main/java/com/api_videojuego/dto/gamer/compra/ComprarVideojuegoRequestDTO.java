package com.api_videojuego.dto.gamer.compra;

import java.math.BigDecimal;

public class ComprarVideojuegoRequestDTO {
	private Integer idUsuario;
	private Integer idVideojuego;
	private BigDecimal precio;

	public ComprarVideojuegoRequestDTO() {
	}

	public ComprarVideojuegoRequestDTO(Integer idUsuario, Integer idVideojuego) {
		this.idUsuario = idUsuario;
		this.idVideojuego = idVideojuego;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public boolean isValid() {
		return idUsuario != null && idVideojuego != null;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

}
