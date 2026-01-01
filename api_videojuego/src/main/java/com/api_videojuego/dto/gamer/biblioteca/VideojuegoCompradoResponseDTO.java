package com.api_videojuego.dto.gamer.biblioteca;

public class VideojuegoCompradoResponseDTO {

	private Integer idVideojuego;
	private String titulo;
	private boolean estadoInstalacion;
	private String portada;

	public VideojuegoCompradoResponseDTO(Integer idVideojuego, String titulo,
			boolean estadoInstalacion) {
		this.idVideojuego = idVideojuego;
		this.titulo = titulo;
		this.estadoInstalacion = estadoInstalacion;
	}

	public Integer getIdVideojuego() {
		return idVideojuego;
	}

	public String getTitulo() {
		return titulo;
	}

	public boolean isEstadoInstalacion() {
		return estadoInstalacion;
	}

	public String getPortada() {
		return portada;
	}

	public void setIdVideojuego(Integer idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setPortada(String portada) {
		this.portada = portada;
	}

	public void setEstadoInstalacion(boolean estadoInstalacion) {
		this.estadoInstalacion = estadoInstalacion;
	}

}
