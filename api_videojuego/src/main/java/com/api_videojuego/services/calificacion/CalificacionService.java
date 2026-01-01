package com.api_videojuego.services.calificacion;

import com.api_videojuego.db.empresa.videojuego.CalificacionVideojuegoDB;
import com.api_videojuego.dto.videojuego.CalificacionVideojuegoRequestDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CalificacionService {

	CalificacionVideojuegoDB calificacionVideojuegoDB;

	public CalificacionService() {
		this.calificacionVideojuegoDB = new CalificacionVideojuegoDB();
	}

	public void registrarCalificacionVideojuego(
			CalificacionVideojuegoRequestDTO calificacionRequest)
			throws ErrorInsertarDB, DatosInvalidos {
		try {
			if (!calificacionRequest.isValid()) {
				throw new DatosInvalidos(
						"Los datos de la calificacion del videojuego no son validos.");
			}
			calificacionVideojuegoDB.registrarCalificacion(calificacionRequest);
		} catch (ErrorInsertarDB e) {
			throw e;
		}
	}

}
