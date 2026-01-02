package com.api_videojuego.services.gamer.grupo;

import com.api_videojuego.db.gamer.grupo.GrupoDB;
import com.api_videojuego.dto.gamer.grupo.EditarEstadoGrupoRquestDTO;
import com.api_videojuego.dto.gamer.grupo.EditarGrupoRequestDTO;
import com.api_videojuego.dto.gamer.grupo.GrupoRequestDTO;
import com.api_videojuego.excepciones.DatoYaExiste;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class GrupoService {

	private GrupoDB grupoDB;

	public GrupoService() {
		this.grupoDB = new GrupoDB();
	}

	public void registrarGrupo(GrupoRequestDTO grupoRequestDTO)
			throws ErrorInsertarDB, DatosInvalidos, DatoYaExiste {
		try {
			if (!grupoRequestDTO.isValid()) {
				throw new DatosInvalidos("Los datos del grupo no son validos");
			}
			if (grupoDB.grupoExiste(grupoRequestDTO.getNombreGrupo())) {
				throw new DatoYaExiste("Ya existe un grupo con este nombre");
			}
			grupoDB.registrarGrupo(grupoRequestDTO);
		} catch (ErrorInsertarDB e) {
			throw e;
		}
	}

	public void editarGrupo(EditarGrupoRequestDTO editarGrupoRequestDTO)
			throws ErrorActualizarRegistro, DatosInvalidos, DatoYaExiste,
			ErrorConsultaDB {
		try {
			if (!editarGrupoRequestDTO.isValid()) {
				throw new DatosInvalidos("Los datos del grupo no son validos");
			}
			if (grupoDB.grupoExisteExcluyendoId(
					editarGrupoRequestDTO.getNuevoNombreGrupo(),
					editarGrupoRequestDTO.getIdGrupo())) {
				throw new DatoYaExiste("Ya existe un grupo con este nombre");
			}
			grupoDB.editarGrupo(editarGrupoRequestDTO);
		} catch (ErrorActualizarRegistro e) {
			throw e;
		} catch (ErrorConsultaDB e) {
			throw e;
		}

	}

	public void cambiarEstadoGrupo(
			EditarEstadoGrupoRquestDTO editarEstadoGrupoRquestDTO)
			throws ErrorActualizarRegistro {
		try {
			grupoDB.cambiarEstadoGrupo(editarEstadoGrupoRquestDTO);
		} catch (ErrorActualizarRegistro e) {
			throw e;
		}
	}
}
