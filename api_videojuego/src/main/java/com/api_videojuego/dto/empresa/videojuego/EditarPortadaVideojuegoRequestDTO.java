package com.api_videojuego.dto.empresa.videojuego;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class EditarPortadaVideojuegoRequestDTO {
	@FormDataParam("idVideojuego")
	private int idVideojuego;
	@FormDataParam("nuevaPortada")
	private FormDataBodyPart nuevaPortada;

	public int getIdVideojuego() {
		return idVideojuego;
	}

	public void setIdVideojuego(int idVideojuego) {
		this.idVideojuego = idVideojuego;
	}

	public FormDataBodyPart getNuevaPortada() {
		return nuevaPortada;
	}

	public void setNuevaPortada(FormDataBodyPart nuevaPortada) {
		this.nuevaPortada = nuevaPortada;
	}

	public long getNuevaPortadaSize() {
		if (nuevaPortada != null)
			return nuevaPortada.getContentDisposition().getSize();

		return 0;
	}

	public String getNuevaPortadaType() {
		if (nuevaPortada != null)
			return nuevaPortada.getMediaType().toString();

		return "";
	}

	public boolean datosValidos() {
		return idVideojuego > 0 && nuevaPortada != null;
	}
}
