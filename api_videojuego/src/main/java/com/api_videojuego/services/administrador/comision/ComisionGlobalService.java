package com.api_videojuego.services.administrador.comision;

import java.time.LocalDate;

import com.api_videojuego.db.administrador.comision.ComisionGlobalDB;
import com.api_videojuego.dto.administrador.comision.EditarComisionGlobalDTO;
import com.api_videojuego.dto.administrador.comision.ObtenerComisionGlobalDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class ComisionGlobalService {

	private ComisionGlobalDB comisionDB;

	public ComisionGlobalService() {
		this.comisionDB = new ComisionGlobalDB();
	}

	public void actualizarComisionGlobal(EditarComisionGlobalDTO comision)
			throws DatosInvalidos, ErrorConsultaDB, ErrorActualizarRegistro {
		try {
			// * Verificar que comision global sea valida */
			if (!comision.esValida()) {
				throw new DatosInvalidos(
						"Los datos de la comision global son invalidos");
			}

			// * Verificar que la comision global exista */
			if (!comisionDB.existeComisionGlobal(comision.getId())) {
				throw new DatosInvalidos(
						"La comision global que se desea actualizar no existe");
			}

			comisionDB.actualizarComisionGlobal(comision, obtenerFechaActual());

		} catch (DatosInvalidos e) {
			throw e;
		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (ErrorActualizarRegistro e) {
			throw e;
		}
	}

	public ObtenerComisionGlobalDTO obtenerComisionGlobal()
			throws ErrorConsultaDB {
		try {
			return comisionDB.obtenerComisionGlobal();
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

	private LocalDate obtenerFechaActual() {
		return LocalDate.now();
	}

}
