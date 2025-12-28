package com.api_videojuego.dto.empresa.videojuego;

import java.util.List;

public class ListaVideojuegosDTO {
	private List<VideojuegoGestionRequestDTO> videojuegos;

	public List<VideojuegoGestionRequestDTO> getVideojuegos() {
		return videojuegos;
	}

	public void setVideojuegos(List<VideojuegoGestionRequestDTO> videojuegos) {
		this.videojuegos = videojuegos;
	}

	public void agregarVideojuego(Integer idVideojuego, String titulo,
			Boolean estado) {
		this.videojuegos
				.add(new VideojuegoGestionRequestDTO(idVideojuego, titulo, estado));
	}
}