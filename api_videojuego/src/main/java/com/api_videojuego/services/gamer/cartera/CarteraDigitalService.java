package com.api_videojuego.services.gamer.cartera;

import java.math.BigDecimal;

import com.api_videojuego.db.gamer.cartera.CarteraDigitalDB;
import com.api_videojuego.dto.gamer.cartera.RecargarCarteraRequestDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class CarteraDigitalService {

	private CarteraDigitalDB carteraDigitalDB = new CarteraDigitalDB();

	public CarteraDigitalService() {
		carteraDigitalDB = new CarteraDigitalDB();
	}

	public void recargarCarteraDigital(RecargarCarteraRequestDTO requestDTO)
			throws DatosInvalidos, ErrorActualizarRegistro {

		try {
			if (!requestDTO.isValid()) {
				throw new DatosInvalidos("Los datos ingresados no son validos");
			}
			carteraDigitalDB.agregarSaldo(requestDTO);
		} catch (ErrorActualizarRegistro e) {
			throw e;
		}
	}

	public BigDecimal obtenerSaldo(Integer idUsuario)
			throws DatosInvalidos, ErrorConsultaDB {
		try {
			if (idUsuario == null || idUsuario <= 0) {
				throw new DatosInvalidos("El ID de usuario no es valido");
			}
			return carteraDigitalDB.obtenerSaldo(idUsuario);
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

}
