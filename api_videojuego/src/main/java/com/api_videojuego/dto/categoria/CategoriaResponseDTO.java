package com.api_videojuego.dto.categoria;

public class CategoriaResponseDTO {

	private Integer idCategoria;
	private String categoria;

	public CategoriaResponseDTO() {
	}

	public CategoriaResponseDTO(Integer idCategoria, String categoria) {
		this.idCategoria = idCategoria;
		this.categoria = categoria;
	}

	// * Getters and Setters */
	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
