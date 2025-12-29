package com.api_videojuego.dto.administrador.categoria;

import java.util.ArrayList;
import java.util.List;

public class ListaCategoriaDTO {

	private List<CategoriaResponseDTO> categorias;

	public ListaCategoriaDTO() {
		this.categorias = new ArrayList<>();
	}

	public ListaCategoriaDTO(List<CategoriaResponseDTO> categorias) {
		this.categorias = categorias;
	}

	// * Getters and Setters */
	public List<CategoriaResponseDTO> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaResponseDTO> categorias) {
		this.categorias = categorias;
	}

	public void agregarCategoria(Integer idCategoria, String categoria) {
		this.categorias.add(new CategoriaResponseDTO(idCategoria, categoria));
	}
}