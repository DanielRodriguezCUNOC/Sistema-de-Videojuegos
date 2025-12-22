package com.api_videojuego.dto.categoria;

public class CategoriaRequestDTO {

	private String categoria;

	public CategoriaRequestDTO() {
	}

	public CategoriaRequestDTO(String categoria) {
		this.categoria = categoria;
	}

	// * Getters and Setters */
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
