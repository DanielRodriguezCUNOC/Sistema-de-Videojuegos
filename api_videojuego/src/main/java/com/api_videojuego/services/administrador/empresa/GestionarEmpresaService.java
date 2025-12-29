package com.api_videojuego.services.administrador.empresa;

import com.api_videojuego.db.administrador.empresa.GestionEmpresaDB;
import com.api_videojuego.dto.administrador.empresa.CambiarEstadoEmpresaDTO;
import com.api_videojuego.dto.administrador.empresa.EditarEmpresaRequetDTO;
import com.api_videojuego.dto.administrador.empresa.ListaEmpresasDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class GestionarEmpresaService {

	private GestionEmpresaDB gestionEmpresaDB;

	public GestionarEmpresaService() {
		this.gestionEmpresaDB = new GestionEmpresaDB();
	}

	public void editarEmpresa(EditarEmpresaRequetDTO editarEmpresaRequetDTO)
			throws ErrorActualizarRegistro, DatosInvalidos {

		try {
			if (!editarEmpresaRequetDTO.isValid()) {
				throw new DatosInvalidos("Datos invalidos");

			}
			gestionEmpresaDB.editarEmpresa(editarEmpresaRequetDTO);
		} catch (ErrorActualizarRegistro e) {
			throw e;
		} catch (DatosInvalidos e) {
			throw e;
		}
	}

	public void cambiarEstadoEmpresa(
			CambiarEstadoEmpresaDTO cambiarEstadoEmpresaDTO)
			throws ErrorActualizarRegistro, DatosInvalidos {

		try {
			if (!cambiarEstadoEmpresaDTO.isValid()) {
				throw new DatosInvalidos("Datos invalidos");

			}
			gestionEmpresaDB.cambiarEstadoEmpresa(cambiarEstadoEmpresaDTO);
		} catch (ErrorActualizarRegistro e) {
			throw e;
		} catch (DatosInvalidos e) {
			throw e;
		}
	}

	public ListaEmpresasDTO obtenerEmpresas() throws ErrorConsultaDB {
		try {
			return gestionEmpresaDB.obtenerEmpresas();
		} catch (ErrorConsultaDB e) {
			throw e;
		}

	}

}
