package com.api_videojuego.dto.administrador.categoria;

import org.glassfish.jersey.media.multipart.FormDataParam;

public class EditarCategoriaDTO {
	@FormDataParam("idCategoria")
	private Integer idCategoria;
	@FormDataParam("categoria")
	private String categoria;

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

	public boolean esCategoriaValida() {
		return categoria != null && !categoria.trim().isEmpty()
				&& idCategoria != null && idCategoria > 0;
	}
}
