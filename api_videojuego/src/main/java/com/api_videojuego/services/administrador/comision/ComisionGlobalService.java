package com.api_videojuego.services.administrador.comision;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import com.api_videojuego.db.administrador.comision.ComisionEspecificaDB;
import com.api_videojuego.db.administrador.comision.ComisionGlobalDB;
import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.administrador.comision.ComisionEspecificaResponseDTO;
import com.api_videojuego.dto.administrador.comision.EditarComisionGlobalDTO;
import com.api_videojuego.dto.administrador.comision.ListaComisionEspecificaDTO;
import com.api_videojuego.dto.administrador.comision.ObtenerComisionGlobalDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class ComisionGlobalService {

	private ComisionGlobalDB comisionDB;
	private ComisionEspecificaDB comisionEspecificaDB;

	public ComisionGlobalService() {
		this.comisionDB = new ComisionGlobalDB();
		this.comisionEspecificaDB = new ComisionEspecificaDB();
	}

	public void actualizarComisionGlobal(EditarComisionGlobalDTO comision)
			throws Exception {
		Connection conn = null;

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

			conn = DBConnectionSingleton.getInstance().getConnection();

			conn.setAutoCommit(false);

			// * Actualizar comision global */
			comisionDB.actualizarComisionGlobal(comision, obtenerFechaActual(), conn);

			// * Actualizar comision especifica si es mayor a la nueva comision global
			// */
			cambiarComisionEspecifica(comision.getComision(), conn);

			conn.commit();

		} catch (DatosInvalidos e) {
			throw e;
		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (ErrorActualizarRegistro e) {
			throw e;
		} catch (Exception e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					throw new Exception("Error al intentar restaurar la base de datos");
				}
			}
			throw new Exception("Error interno del servidor");
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
				}
			}
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

	public void cambiarComisionEspecifica(BigDecimal nuevaComisionGlobal,
			Connection conn) throws Exception {
		try {
			ListaComisionEspecificaDTO comisiones = comisionEspecificaDB
					.listaComisionEspecifica(conn);

			for (ComisionEspecificaResponseDTO comision : comisiones
					.getComisiones()) {
				if (comision.getComisionEspecifica()
						.compareTo(nuevaComisionGlobal) > 0) {
					comision.setComisionEspecifica(nuevaComisionGlobal);
					comisionEspecificaDB.cambiarComisionEspecifica(comision,
							obtenerFechaActual(), conn);
				}
			}
		} catch (ErrorActualizarRegistro e) {
			throw new ErrorActualizarRegistro(
					"Error al actualizar las comisiones especificas");
		} catch (Exception e) {
			throw new RuntimeException(
					"Error interno del servidor al actualizar las comisiones especificas");
		}
	}

}
