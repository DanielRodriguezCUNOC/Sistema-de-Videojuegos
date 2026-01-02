package com.api_videojuego.services.gamer.grupo;

import java.util.List;

import com.api_videojuego.db.gamer.grupo.IntegranteGrupoDB;
import com.api_videojuego.dto.gamer.grupo.EliminarIntegranteDTO;
import com.api_videojuego.dto.gamer.grupo.GrupoResponseDTO;
import com.api_videojuego.dto.gamer.grupo.NuevoIntegranteRequestDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEliminarRegistro;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class IntegranteGrupoService {

	private IntegranteGrupoDB integranteGrupoDB;

	public IntegranteGrupoService() {
		this.integranteGrupoDB = new IntegranteGrupoDB();
	}

	public List<GrupoResponseDTO> obtenerGrupo(Integer idUsuario)
			throws ErrorConsultaDB {
		try {
			return integranteGrupoDB.obtenerGruposUsuario(idUsuario);
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

	public void eliminarIntegrante(EliminarIntegranteDTO eliminarIntegranteDTO)
			throws ErrorEliminarRegistro {
		try {
			integranteGrupoDB.verificarIntegranteEliminable(eliminarIntegranteDTO);
			integranteGrupoDB.eliminarIntegrante(eliminarIntegranteDTO);
		} catch (ErrorEliminarRegistro e) {
			throw e;
		}
	}

	public void agregarIntegrante(
			NuevoIntegranteRequestDTO nuevoIntegranteRequestDTO)
			throws DatosInvalidos, ErrorInsertarDB {
		try {
			if (!nuevoIntegranteRequestDTO.isValid()) {
				throw new DatosInvalidos("Los datos ingresado no son validos");
			}
			integranteGrupoDB.agregarIntegrante(nuevoIntegranteRequestDTO);
		} catch (ErrorInsertarDB e) {
			throw e;
		}

	}

}
