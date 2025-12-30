package com.api_videojuego.services.administrador.videojuego;

import com.api_videojuego.db.administrador.videojuego.SolicitudCategoriaDB;
import com.api_videojuego.dto.administrador.videojuego.ListaSolicitudVideojuegoDTO;
import com.api_videojuego.dto.administrador.videojuego.SolicitudVideojuegoRequestDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEliminarRegistro;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class SolicitudVideojuegoService {

	private SolicitudCategoriaDB solicitudCategoriaDB;

	public SolicitudVideojuegoService() {
		this.solicitudCategoriaDB = new SolicitudCategoriaDB();
	}

	public void gestionarAccionesSolicitud(
			SolicitudVideojuegoRequestDTO solicitudDTO)
			throws ErrorInsertarDB, ErrorEliminarRegistro, ErrorConsultaDB {

		try {
			if (solicitudDTO.getEstado().equals("aprobado")) {
				solicitudCategoriaDB.aceptarSolicitudCategoria(solicitudDTO);
				return;
			}
			else if (solicitudDTO.getEstado().equals("rechazado")) {
				solicitudCategoriaDB.eliminarRegistro(solicitudDTO.getIdSolicitud());
			}

			int solicitudesRestantes = solicitudCategoriaDB
					.solicitudesRestantesPorVideojuego(solicitudDTO.getIdVideojuego());

			if (solicitudesRestantes == 0) {
				if (!solicitudCategoriaDB
						.tieneCategoriasAceptadas(solicitudDTO.getIdVideojuego())) {

					solicitudCategoriaDB.rechazarSolicitudCategoria(solicitudDTO);
				}
			}
		} catch (ErrorInsertarDB e) {
			throw e;
		} catch (ErrorEliminarRegistro e) {
			throw e;
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

	public ListaSolicitudVideojuegoDTO listarSolicitudesVideojuego()
			throws ErrorConsultaDB {
		try {
			return solicitudCategoriaDB.listarSolicitudesVideojuego();
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}
}
