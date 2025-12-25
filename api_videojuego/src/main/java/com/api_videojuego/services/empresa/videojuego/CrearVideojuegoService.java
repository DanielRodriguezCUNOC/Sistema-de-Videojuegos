package com.api_videojuego.services.empresa.videojuego;

import java.io.InputStream;

import com.api_videojuego.db.empresa.videojuego.CrearVideojuegoDB;
import com.api_videojuego.db.empresa.videojuego.ImagenJuegoDB;
import com.api_videojuego.dto.empresa.videojuego.VideojuegoRequestDTO;
import com.api_videojuego.excepciones.DatoYaExiste;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CrearVideojuegoService {

	private CrearVideojuegoDB crearVideojuegoDB;

	private ImagenJuegoDB imagenJuegoDB;

	public CrearVideojuegoService() {
		this.crearVideojuegoDB = new CrearVideojuegoDB();
		this.imagenJuegoDB = new ImagenJuegoDB();
	}

	public void crearVideojuego(VideojuegoRequestDTO videojuego)
			throws ErrorConsultaDB, DatoYaExiste, DatosInvalidos, ErrorInsertarDB {

		try {

			// *Verificamos que los datos sean validos */
			if (!videojuego.esVideojuegoValido()) {
				throw new DatosInvalidos("Los datos del videojuego no son validos");
			}

			// * Verificamos si existe un videojuego con el mismo titulo */
			if (crearVideojuegoDB.existeVideojuegoPorTitulo(videojuego.getTitulo())) {
				throw new DatoYaExiste("Ya existe un videojuego con el mismo titulo");
			}

			// * Registrar el videojuego */
			Integer idVideojuego = crearVideojuegoDB.registrarVideojuego(videojuego);

			// * Guardar la imagen del videojuego */
			imagenJuegoDB.guardarImagen(idVideojuego,
					videojuego.getImagenPart().getValueAs(InputStream.class));

		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (DatoYaExiste e) {
			throw e;
		} catch (DatosInvalidos e) {
			throw e;
		} catch (ErrorInsertarDB e) {
			throw e;
		}

	}

}
