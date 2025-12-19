package com.api_videojuego.dto.categoria;

import org.glassfish.jersey.media.multipart.FormDataParam;

public class CrearCategoriaDTO {

	@FormDataParam("categoria")
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
