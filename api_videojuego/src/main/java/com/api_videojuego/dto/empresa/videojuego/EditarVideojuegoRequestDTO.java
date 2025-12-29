package com.api_videojuego.dto.empresa.videojuego;

public class EditarVideojuegoRequestDTO {
	private Integer idVideojuego;
	private String titulo;
	private String descripcion;
	private Double precio;
	private String recursosMinimos;

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getRecursosMinimos() {
		return recursosMinimos;
	}

	public void setRecursosMinimos(String recursosMinimos) {
		this.recursosMinimos = recursosMinimos;
	}

	public boolean datosValidos() {
		return idVideojuego != null && titulo != null && !titulo.isBlank()
				&& descripcion != null && !descripcion.isBlank() && precio != null
				&& precio >= 0 && recursosMinimos != null && !recursosMinimos.isBlank();
	}
}
