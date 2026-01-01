package com.api_videojuego.dto.videojuego;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PerfilVideojuegoResponseDTO {

	private String titulo;
	private List<String> categorias;
	private String clasificacion;
	private String descripcion;
	private String recursosMinimos;
	private BigDecimal precio;
	private String imagenPortada;

	public PerfilVideojuegoResponseDTO() {
		this.categorias = new ArrayList<>();
	}

	public PerfilVideojuegoResponseDTO(String titulo, List<String> categorias,
			String clasificacion, String descripcion, String recursosMinimos,
			BigDecimal precio, String imagenPortada) {
		this.titulo = titulo;
		this.categorias = categorias != null ? categorias : new ArrayList<>();
		this.clasificacion = clasificacion;
		this.descripcion = descripcion;
		this.recursosMinimos = recursosMinimos;
		this.precio = precio;
		this.imagenPortada = imagenPortada;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
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

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getImagenPortada() {
		return imagenPortada;
	}

	public void setImagenPortada(String imagenPortada) {
		this.imagenPortada = imagenPortada;
	}

	public void addCategoria(String categoria) {
		this.categorias.add(categoria);
	}

}
