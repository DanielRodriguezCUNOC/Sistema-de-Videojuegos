package com.api_videojuego.services.perfiles;

import com.api_videojuego.db.administrador.videojuego.VideojuegoDB;
import com.api_videojuego.db.empresa.videojuego.ImagenJuegoDB;
import com.api_videojuego.dto.videojuego.PerfilVideojuegoResponseDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ExcepcionInesperada;
import com.api_videojuego.utils.ConvertirImagen;

public class PerfilVideojuegoService {

	private VideojuegoDB videojuegoDB;
	private ImagenJuegoDB imagenJuegoDB;
	private ConvertirImagen convertirImagen;

	public PerfilVideojuegoService() {
		this.videojuegoDB = new VideojuegoDB();
		this.imagenJuegoDB = new ImagenJuegoDB();
		this.convertirImagen = new ConvertirImagen();
	}

	public PerfilVideojuegoResponseDTO obtenerInformacionVideojuego(
			Integer idVideojuego) throws ErrorConsultaDB, ExcepcionInesperada {
		try {

			// * Obtener los datos del videojuego excepto la imagen */
			PerfilVideojuegoResponseDTO perfil = videojuegoDB
					.obtenerPerfilVideojuego(idVideojuego);

			// * Obtener la portada del videojuego */
			String portada = obtenerPortada(idVideojuego);
			perfil.setImagenPortada(portada);

			// * Retornar el objeto con los datos completos */
			return perfil;

		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (ExcepcionInesperada e) {
			throw e;
		}
	}

	public String obtenerPortada(Integer idVideojuego)
			throws ExcepcionInesperada, ErrorConsultaDB {

		// * Obtener la imagen y convertirla en base 64 */
		byte[] imagenBytes = imagenJuegoDB
				.obtenerImagenPorIdVideojuego(idVideojuego);
		String imagenBase64 = convertirImagen.convertirImagenBase64(imagenBytes);
		return imagenBase64;

	}

}
