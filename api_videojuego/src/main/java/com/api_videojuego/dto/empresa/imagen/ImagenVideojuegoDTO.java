package com.api_videojuego.dto.empresa.imagen;

public class ImagenVideojuegoDTO {

	private Integer id;
	private Integer idVideojuego;
	private String tituloVideojuego;
	private byte[] imagen;

	public ImagenVideojuegoDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public String getTituloVideojuego() {
		return tituloVideojuego;
	}

	public void setTituloVideojuego(String tituloVideojuego) {
		this.tituloVideojuego = tituloVideojuego;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
}
