package com.api_videojuego.dto.categoria;

import java.util.List;

public class ListaCategoriaDTO {

	private List<CategoriaDTO> categorias;

	public ListaCategoriaDTO() {
	}

	public ListaCategoriaDTO(List<CategoriaDTO> categorias) {
		this.categorias = categorias;
	}

	// * Getters and Setters */
	public List<CategoriaDTO> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaDTO> categorias) {
		this.categorias = categorias;
	}

	public void agregarCategoria(Integer idCategoria, String categoria) {
		this.categorias.add(new CategoriaDTO(idCategoria, categoria));
	}
}