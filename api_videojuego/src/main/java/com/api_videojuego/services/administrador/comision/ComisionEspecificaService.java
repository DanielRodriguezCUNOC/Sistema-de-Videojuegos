package com.api_videojuego.services.administrador.comision;

import java.sql.Connection;
import java.time.LocalDate;

import com.api_videojuego.db.administrador.comision.ComisionEspecificaDB;
import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.administrador.comision.ComisionEspecificaRequestDTO;
import com.api_videojuego.dto.administrador.comision.EditarComisionEspecificaDTO;
import com.api_videojuego.dto.administrador.comision.ListaComisionEspecificaDTO;
import com.api_videojuego.excepciones.DatoYaExiste;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class ComisionEspecificaService {

	private ComisionEspecificaDB comisionDB;

	public ComisionEspecificaService() {
		this.comisionDB = new ComisionEspecificaDB();
	}

	public void actualizarComisionEspecifica(EditarComisionEspecificaDTO comision)
			throws Exception {
		try {
			// * Validamos que los datos sean correctos */
			if (!comision.esComisionValida()) {
				throw new DatosInvalidos("Los datos no son correctos");
			}
			// * Validamos que exista una comision específica para la empresa */
			if (!comisionDB.existeComisionEspecifica(comision.getId())) {
				throw new DatosInvalidos(
						"No se encontró la comisión específica a actualizar");
			}
			// * Actualizamos la comision */
			comisionDB.actualizarComisionEspecifica(comision, obtenerFechaActual());
		} catch (DatosInvalidos e) {
			throw e;
		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (ErrorActualizarRegistro e) {
			throw e;
		} catch (Exception e) {
		}
	}

	public void registrarComisionEspecifica(ComisionEspecificaRequestDTO comision)
			throws Exception {
		try {
			// * Validamos que los datos sean correctos */
			if (!comision.esComisionValida()) {
				throw new DatosInvalidos("Los datos no son correctos");
			}

			// *Obtenemos el id de la empresa */
			Integer idEmpresa = obtenerIdEmpresaPorNombre(
					comision.getNombreEmpresa());

			// * Validamos que no exista una comision específica para la empresa */
			if (comisionDB.existeComisionEspecifica(idEmpresa)) {
				throw new DatoYaExiste(
						"Ya existe una comisión específica para esta empresa");
			}
			// * Registramos la comision */
			comisionDB.registrarComisionEspecifica(comision, obtenerFechaActual(),
					idEmpresa);
		} catch (DatosInvalidos e) {
			throw e;
		} catch (DatoYaExiste e) {
			throw e;
		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (ErrorInsertarDB e) {
			throw e;
		} catch (Exception e) {
		}
	}

	private LocalDate obtenerFechaActual() {
		return LocalDate.now();
	}

	private Integer obtenerIdEmpresaPorNombre(String nombreEmpresa)
			throws Exception {
		return comisionDB.obtenerIdEmpresaporNombre(nombreEmpresa);
	}

	public ListaComisionEspecificaDTO obtenerComisionesEspecificas()
			throws Exception {
		Connection conn = null;
		try {
			conn = DBConnectionSingleton.getInstance().getConnection();
			return (ListaComisionEspecificaDTO) comisionDB
					.listaComisionEspecifica(conn);
		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (Exception e) {
		}
		return null;
	}

}
