package com.api_videojuego.services.gamer.biblioteca;

import java.util.List;

import com.api_videojuego.db.empresa.videojuego.ImagenJuegoDB;
import com.api_videojuego.db.empresa.videojuego.VideojuegoCompradoDB;
import com.api_videojuego.dto.gamer.biblioteca.VideojuegoCompradoRequestDTO;
import com.api_videojuego.dto.gamer.biblioteca.VideojuegoCompradoResponseDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ExcepcionInesperada;
import com.api_videojuego.utils.ConvertirImagen;

public class BibliotecaService {

	private VideojuegoCompradoDB videojuegoCompradoDB;
	private ConvertirImagen convertirImagen;
	private ImagenJuegoDB imagenJuegoDB;

	public BibliotecaService() {
		this.videojuegoCompradoDB = new VideojuegoCompradoDB();
		this.convertirImagen = new ConvertirImagen();
		this.imagenJuegoDB = new ImagenJuegoDB();
	}

	public List<VideojuegoCompradoResponseDTO> obtenerVideojuegos(
			Integer idUsuario)
			throws ErrorConsultaDB, ExcepcionInesperada, DatosInvalidos {
		List<VideojuegoCompradoResponseDTO> videojuegosComprados;

		try {
			if (idUsuario == null) {
				throw new DatosInvalidos("El id no es valido");

			}

			videojuegosComprados = videojuegoCompradoDB
					.obtenerVideojuegosCompradosPorGamer(idUsuario);

			/*
			 * Obtenemos las imagenes de cada videojuego y las convertimos a base 64
			 */

			for (VideojuegoCompradoResponseDTO videojuegoCompradoResponseDTO : videojuegosComprados) {

				byte[] imagenBytes = imagenJuegoDB.obtenerImagenPorIdVideojuego(
						videojuegoCompradoResponseDTO.getIdVideojuego());

				if (imagenBytes != null) {
					String imagenBase64 = convertirImagen
							.convertirImagenBase64(imagenBytes);
					videojuegoCompradoResponseDTO.setPortada(imagenBase64);
				}
			}

			return videojuegosComprados;

		} catch (ErrorConsultaDB e) {
			throw e;
		}

	}

	public void cambiarEstadoInstalacionVideojuego(
			VideojuegoCompradoRequestDTO videojuegoCompradoRequestDTO)
			throws ErrorActualizarRegistro {
		try {
			videojuegoCompradoDB
					.actualizaEstadoInstalacionVideojuego(videojuegoCompradoRequestDTO);
		} catch (ErrorActualizarRegistro e) {
			throw e;
		}
	}
}
