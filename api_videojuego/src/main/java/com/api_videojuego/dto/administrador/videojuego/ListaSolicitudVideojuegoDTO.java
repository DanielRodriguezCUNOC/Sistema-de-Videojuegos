package com.api_videojuego.dto.administrador.videojuego;

import java.util.List;

public class ListaSolicitudVideojuegoDTO {

	private List<SolicitudVideojuegoResponseDTO> solicitudes;

	public List<SolicitudVideojuegoResponseDTO> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<SolicitudVideojuegoResponseDTO> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public void agregarSolicitud(Integer idSolicitud, Integer idVideojuego,
			String nombreVideojuego, String nombreCategoria, String estadoSolicitud) {
		this.solicitudes.add(new SolicitudVideojuegoResponseDTO(idSolicitud,
				idVideojuego, nombreVideojuego, nombreCategoria, estadoSolicitud));
	}

}
