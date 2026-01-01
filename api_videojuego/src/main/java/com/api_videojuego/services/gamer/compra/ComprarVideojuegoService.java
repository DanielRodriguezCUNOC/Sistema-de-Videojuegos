package com.api_videojuego.services.gamer.compra;

import java.math.BigDecimal;

import com.api_videojuego.db.administrador.comision.ComisionEspecificaDB;
import com.api_videojuego.db.administrador.comision.ComisionGlobalDB;
import com.api_videojuego.db.empresa.videojuego.VideojuegoCompradoDB;
import com.api_videojuego.db.empresa.videojuego.VideojuegoDesarrolladoraDB;
import com.api_videojuego.db.pago.PagoDB;
import com.api_videojuego.dto.gamer.compra.ComprarVideojuegoRequestDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;
import com.api_videojuego.utils.ValidarCompra;

public class ComprarVideojuegoService {

	private ComisionGlobalDB comisionGlobalDB;
	private PagoDB pagoDB;
	private VideojuegoDesarrolladoraDB videojuegoDesarrolladoraDB;
	private ComisionEspecificaDB comisionEspecificaDB;
	private ValidarCompra validarCompra;

	private VideojuegoCompradoDB videojuegoCompradoDB;

	public ComprarVideojuegoService() {
		this.comisionGlobalDB = new ComisionGlobalDB();
		this.pagoDB = new PagoDB();
		this.videojuegoDesarrolladoraDB = new VideojuegoDesarrolladoraDB();
		this.comisionEspecificaDB = new ComisionEspecificaDB();
		this.validarCompra = new ValidarCompra();
		this.videojuegoCompradoDB = new VideojuegoCompradoDB();
	}

	public void comprarVideojuego(ComprarVideojuegoRequestDTO requestDTO)
			throws ErrorConsultaDB, ErrorInsertarDB, DatosInvalidos,
			ErrorActualizarRegistro {
		try {
			// *Validar requisitos de compra antes de procesar
			validarRequisitosCompra(requestDTO);

			/*
			 * Obtener la comisión global actual
			 */
			BigDecimal comisionGlobal = comisionGlobalDB.consultarComisionGlobal();

			/*
			 * Registrar el pago del videojuego con la comisión global
			 */
			Integer idPago = pagoDB.registrarPagoVideojuego(requestDTO,
					comisionGlobal);

			/*
			 * Si la empresa tiene comisión, registrar el pago de la comisión a la
			 * empresa
			 */
			Integer idEmpresa = empresaTieneComision(requestDTO.getIdVideojuego());

			if (idEmpresa != null) {
				BigDecimal comisionEspecifica = comisionEspecificaDB
						.consultarComisionEspecificaPorEmpresa(idEmpresa);
				registrarPagoComisionEmpresa(idEmpresa, comisionEspecifica, idPago);
			}
			// * Se agrega un nuevo registro a la tabla videojuego_comprado */
			videojuegoCompradoDB.registrarVideojuegoComprado(
					requestDTO.getIdUsuario(), requestDTO.getIdVideojuego());

		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (ErrorInsertarDB e) {
			throw e;
		} catch (ErrorActualizarRegistro e) {
			throw e;
		}
	}

	/*
	 * Verificar si la empresa desarrolladora del videojuego tiene una comisión,
	 * si es así, retornamos el id de la empresa
	 */
	public Integer empresaTieneComision(Integer idVideojuego)
			throws ErrorConsultaDB {
		Integer idEmpresa = videojuegoDesarrolladoraDB
				.idEmpresaPorIdVidejuego(idVideojuego);
		if (idEmpresa != null
				&& comisionEspecificaDB.existeComisionEspecifica(idEmpresa)) {
			return idEmpresa;
		}
		return null;
	}

	/*
	 * Registrar el pago de la comisión a la empresa desarrolladora
	 */
	public void registrarPagoComisionEmpresa(Integer idEmpresa,
			BigDecimal comisionEspecifica, Integer idPago) throws ErrorInsertarDB {
		pagoDB.registrarPagoComisionEmpresa(idEmpresa, comisionEspecifica, idPago);
	}

	/*
	 * Valida todos los requisitos necesarios para realizar la compra
	 */
	public void validarRequisitosCompra(ComprarVideojuegoRequestDTO requestDTO)
			throws DatosInvalidos, ErrorConsultaDB {

		// * Validar datos básicos del request
		if (!requestDTO.isValid()) {
			throw new DatosInvalidos("Los datos de la compra son inválidos");
		}

		// * Validar requisitos de edad y fondos
		if (!validarCompra.validarRequisitosCompra(requestDTO)) {
			throw new DatosInvalidos(
					"El usuario no cumple con los requisitos para comprar este videojuego");
		}
	}

}
