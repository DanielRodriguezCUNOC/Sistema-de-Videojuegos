package com.api_videojuego.dto.administrador.categoria;

public class CrearCategoriaDTO {

	private String categoria;

	// * Getters and Setters */
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public boolean esCategoriaValida() {
		return categoria != null && !categoria.trim().isEmpty();
	}

}
