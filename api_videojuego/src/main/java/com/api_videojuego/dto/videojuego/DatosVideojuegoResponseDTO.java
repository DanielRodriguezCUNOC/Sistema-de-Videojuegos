package com.api_videojuego.dto.videojuego;

public class DatosVideojuegoResponseDTO {

	private String titulo;
	private String[] categorias;
	private String clasificacion;
	private String nombreEmpresa;
	private String descripcion;
	private String recursosMinimos;
	private String precio;
	private String imagenPortada;

	public DatosVideojuegoResponseDTO() {
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String[] getCategorias() {
		return categorias;
	}

	public void setCategorias(String[] categorias) {
		this.categorias = categorias;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRecursosMinimos() {
		return recursosMinimos;
	}

	public void setRecursosMinimos(String recursosMinimos) {
		this.recursosMinimos = recursosMinimos;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getImagenPortada() {
		return imagenPortada;
	}

	public void setImagenPortada(String imagenPortada) {
		this.imagenPortada = imagenPortada;
	}

}
