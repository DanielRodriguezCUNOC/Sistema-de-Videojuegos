package com.api_videojuego.services.tienda;

import java.util.List;

import com.api_videojuego.db.tienda.TiendaDB;
import com.api_videojuego.dto.videojuego.DatosVideojuegoTiendaDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ExcepcionInesperada;
import com.api_videojuego.utils.ConvertirImagen;

public class TiendaService {

	private TiendaDB tiendaDB;
	private ConvertirImagen convertirImagen = new ConvertirImagen();

	public TiendaService() {
		this.tiendaDB = new TiendaDB();
		convertirImagen = new ConvertirImagen();
	}

	public List<DatosVideojuegoTiendaDTO> obtenerVideojuegosParaTienda(int offset,
			int limit) throws ErrorConsultaDB, DatosInvalidos, ExcepcionInesperada {
		List<DatosVideojuegoTiendaDTO> videojuegos;
		try {
			if (offset < 0 || limit <= 0) {
				throw new DatosInvalidos("Los limites no son validos");
			}
			// *Obtenemos los videojuegos de la base de datos */
			videojuegos = tiendaDB.obtenerVideojuegosParaTienda(offset, limit);

			// *Convertimos las imagenes a base64 */
			for (DatosVideojuegoTiendaDTO datosVideojuegoTiendaDTO : videojuegos) {
				String imagenBase64 = convertirImagen.convertirImagenBase64(
						datosVideojuegoTiendaDTO.getImagenPortadaBytes());
				datosVideojuegoTiendaDTO.setImagenPortada(imagenBase64);
			}
			// *Retornamos los videojuegos */
			return videojuegos;
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

	public DatosVideojuegoTiendaDTO obtenerVideojuegosPorTitulo(String titulo)
			throws ErrorConsultaDB, DatosInvalidos, ExcepcionInesperada {
		DatosVideojuegoTiendaDTO videojuego;
		try {
			if (titulo == null || titulo.isBlank()) {
				throw new DatosInvalidos("El titulo no es valido");
			}
			// *Obtenemos el videojuego de la base de datos */
			videojuego = tiendaDB.obtenerVideojuegosPorTitulo(titulo);

			// *Convertimos la imagen a base64 */
			String imagenBase64 = convertirImagen
					.convertirImagenBase64(videojuego.getImagenPortadaBytes());
			videojuego.setImagenPortada(imagenBase64);

			// *Retornamos el videojuego */
			return videojuego;
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

	public List<DatosVideojuegoTiendaDTO> obtenerVideojuegosPorCategoria(
			int idCategoria)
			throws ErrorConsultaDB, DatosInvalidos, ExcepcionInesperada {
		List<DatosVideojuegoTiendaDTO> videojuegos;
		try {
			if (idCategoria <= 0) {
				throw new DatosInvalidos("El ID de la categoria no es valido");
			}
			// *Obtenemos los videojuegos de la base de datos */
			videojuegos = tiendaDB.obtenerVideojuegosPorCategoria(idCategoria);

			// *Convertimos las imagenes a base64 */
			for (DatosVideojuegoTiendaDTO datosVideojuegoTiendaDTO : videojuegos) {
				String imagenBase64 = convertirImagen.convertirImagenBase64(
						datosVideojuegoTiendaDTO.getImagenPortadaBytes());
				datosVideojuegoTiendaDTO.setImagenPortada(imagenBase64);
			}

			// *Retornamos los videojuegos */
			return videojuegos;
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}
}
