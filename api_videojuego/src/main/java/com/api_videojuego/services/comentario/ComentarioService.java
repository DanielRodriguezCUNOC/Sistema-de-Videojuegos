package com.api_videojuego.services.comentario;

import java.util.List;

import com.api_videojuego.db.empresa.videojuego.ComentarioDB;
import com.api_videojuego.dto.videojuego.ComentarioRequestDTO;
import com.api_videojuego.dto.videojuego.ComentarioResponseDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class ComentarioService {

	private ComentarioDB comentarioDB;

	public ComentarioService() {
		this.comentarioDB = new ComentarioDB();
	}

	public List<ComentarioResponseDTO> obtenerComentariosPorVideojuego(
			Integer idVideojuego) throws ErrorConsultaDB {
		try {
			return comentarioDB.obtenerComentariosPorVideojuego(idVideojuego);

		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

	public void agregarComentario(ComentarioRequestDTO comentarioRequest)
			throws ErrorInsertarDB, DatosInvalidos {
		try {
			if (!comentarioRequest.isValid()) {
				throw new DatosInvalidos("Los datos del comentario no son válidos.");

			}
			comentarioDB.registrarComentarioVideojuego(comentarioRequest);
		} catch (ErrorInsertarDB e) {
			throw e;
		}

	}

}
