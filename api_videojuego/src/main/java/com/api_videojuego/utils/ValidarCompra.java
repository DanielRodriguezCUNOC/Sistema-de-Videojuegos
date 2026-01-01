package com.api_videojuego.utils;

import java.math.BigDecimal;

import com.api_videojuego.db.administrador.clasificacion.ClasificacionDB;
import com.api_videojuego.db.gamer.cartera.CarteraDigitalDB;
import com.api_videojuego.db.gamer.usuario.UsuarioGamerDB;
import com.api_videojuego.dto.gamer.compra.ComprarVideojuegoRequestDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class ValidarCompra {
	private UsuarioGamerDB usuarioGamerDB;
	private CarteraDigitalDB carteraDigitalDB;
	private ClasificacionDB clasificacionDB;

	public ValidarCompra() {
		this.usuarioGamerDB = new UsuarioGamerDB();
		this.carteraDigitalDB = new CarteraDigitalDB();
		this.clasificacionDB = new ClasificacionDB();
	}

	/*
	 * Valida todos los requisitos para la compra de un videojuego
	 */
	public boolean validarRequisitosCompra(ComprarVideojuegoRequestDTO requestDTO)
			throws ErrorConsultaDB {
		// * Validar edad del usuario
		if (!validarEdadUsuario(requestDTO.getIdUsuario(),
				requestDTO.getIdVideojuego())) {
			return false;
		}

		// * Validar fondos suficientes
		if (!validarFondosSuficientes(requestDTO.getIdUsuario(),
				requestDTO.getPrecio())) {
			return false;
		}

		return true;
	}

	/*
	 * Valida que la edad del usuario sea igual o mayor a la edad requerida del
	 * videojuego
	 */
	public boolean validarEdadUsuario(Integer idUsuario, Integer idVideojuego)
			throws ErrorConsultaDB {
		try {
			Integer edadUsuario = usuarioGamerDB.obtenerEdadUsuario(idUsuario);

			/*
			 * Dado que la clasificacion puede venir un caracter realizamos una
			 * conversion
			 */
			Integer edadMinimaVideojuego;

			// * Evaluar si la clasificacion es "A" */
			if (clasificacionDB.obtenerEdadMinimaVideojuego(idVideojuego).toString()
					.equalsIgnoreCase("A")) {
				// * Asignar 0 si es para todo publico */
				edadMinimaVideojuego = 0;
			}
			else {
				edadMinimaVideojuego = Integer.parseInt(
						clasificacionDB.obtenerEdadMinimaVideojuego(idVideojuego));
			}

			return edadUsuario >= edadMinimaVideojuego;
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

	/*
	 * Valida que el usuario tenga fondos suficientes para la compra
	 */
	public boolean validarFondosSuficientes(Integer idUsuario,
			BigDecimal precioVideojuego) throws ErrorConsultaDB {
		try {

			// *Obtener fondos del usuario */
			BigDecimal fondosDisponibles = carteraDigitalDB.obtenerSaldo(idUsuario);
			// *Comparar fondos con el precio del videojuego */
			return fondosDisponibles.compareTo(precioVideojuego) >= 0;
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}
}
