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
			throws Exception {

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

			// * Actualizar comision global */
			comisionDB.actualizarComisionGlobal(comision, obtenerFechaActual());

		} catch (DatosInvalidos e) {
			throw e;
		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (ErrorActualizarRegistro e) {
			throw e;
		} catch (Exception e) {
		}

	}

	public ObtenerComisionGlobalDTO obtenerComisionGlobal() throws Exception {
		try {
			return comisionDB.obtenerComisionGlobal();
		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Error interno del servidor");

		}
	}

	private LocalDate obtenerFechaActual() {
		return LocalDate.now();
	}

}
