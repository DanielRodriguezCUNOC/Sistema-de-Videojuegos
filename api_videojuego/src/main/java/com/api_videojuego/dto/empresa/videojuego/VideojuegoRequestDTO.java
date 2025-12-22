package com.api_videojuego.dto.empresa.videojuego;

import java.util.List;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class VideojuegoRequestDTO {

	@FormDataParam("idUsuarioEmpresa")
	private String idUsuarioEmpresa;
	@FormDataParam("titulo")
	private String titulo;
	@FormDataParam("descripcion")
	private String descripcion;
	@FormDataParam("fechaLanzamiento")
	private String fechaLanzamiento;
	@FormDataParam("precio")
	private String precio;
	@FormDataParam("recursosMinimos")
	private String recursosMinimos;
	@FormDataParam("imagen")
	private FormDataBodyPart imagenPart;
	@FormDataParam("clasificacion")
	private String clasificacion;
	@FormDataParam("categorias")
	private List<String> categorias;

	// * Getters and Setters */
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

	public String getFechaLanzamiento() {
		return fechaLanzamiento;
	}

	public void setFechaLanzamiento(String fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getRecursosMinimos() {
		return recursosMinimos;
	}

	public void setRecursosMinimos(String recursosMinimos) {
		this.recursosMinimos = recursosMinimos;
	}

	public FormDataBodyPart getImagenPart() {
		return imagenPart;
	}

	public void setImagenPart(FormDataBodyPart imagenPart) {
		this.imagenPart = imagenPart;
	}

	public String getIdUsuarioEmpresa() {
		return idUsuarioEmpresa;
	}

	public void setIdUsuarioEmpresa(String idUsuarioEmpresa) {
		this.idUsuarioEmpresa = idUsuarioEmpresa;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public List<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}

	public boolean esVideojuegoValido() {
		return titulo != null && !titulo.isEmpty() && descripcion != null
				&& !descripcion.isEmpty() && fechaLanzamiento != null
				&& !fechaLanzamiento.isEmpty() && precio != null && !precio.isEmpty()
				&& recursosMinimos != null && !recursosMinimos.isEmpty()
				&& imagenPart != null && categorias != null && !categorias.isEmpty()
				&& clasificacion != null && !clasificacion.isEmpty();
	}

}
