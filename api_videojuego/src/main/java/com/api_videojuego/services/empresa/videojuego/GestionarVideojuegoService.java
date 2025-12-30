package com.api_videojuego.services.empresa.videojuego;

import java.io.IOException;
import java.io.InputStream;

import com.api_videojuego.db.empresa.usuario.UsuarioEmpresaDB;
import com.api_videojuego.db.empresa.videojuego.GestionVideojuegoDB;
import com.api_videojuego.dto.empresa.videojuego.EditarEstadoVideojuegoDTO;
import com.api_videojuego.dto.empresa.videojuego.EditarPortadaVideojuegoRequestDTO;
import com.api_videojuego.dto.empresa.videojuego.EditarVideojuegoRequestDTO;
import com.api_videojuego.dto.empresa.videojuego.EditarVideojuegoResponseDTO;
import com.api_videojuego.dto.empresa.videojuego.ListaVideojuegosDTO;
import com.api_videojuego.excepciones.AvatarExcepcion;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ExcepcionInesperada;
import com.api_videojuego.utils.ConfiguracionAvatar;
import com.api_videojuego.utils.ConvertirImagen;

public class GestionarVideojuegoService {

	private GestionVideojuegoDB gestionVideojuegoDB;

	public GestionarVideojuegoService() {
		this.gestionVideojuegoDB = new GestionVideojuegoDB();
	}

	public void cambiarEstadoVideojuego(
			EditarEstadoVideojuegoDTO editarEstadoVideojuegoDTO)
			throws ErrorActualizarRegistro, DatosInvalidos {

		try {

			if (!editarEstadoVideojuegoDTO.datosValidos()) {
				throw new DatosInvalidos("Los datos proporcionados no son validos");
			}
			gestionVideojuegoDB.cambiarEstadoVideojuego(editarEstadoVideojuegoDTO);
		} catch (ErrorActualizarRegistro e) {
			throw e;
		}

	}

	public void cambiarPortadaVideojuego(
			EditarPortadaVideojuegoRequestDTO editarPortadaVideojuegoDTO)
			throws DatosInvalidos, AvatarExcepcion, ExcepcionInesperada, IOException,
			ErrorActualizarRegistro {

		ConvertirImagen convertirImagen = new ConvertirImagen();
		byte[] nuevaPortada = null;

		if (!editarPortadaVideojuegoDTO.datosValidos()) {
			throw new DatosInvalidos("Los datos proporcionados no son validos");
		}

		if (editarPortadaVideojuegoDTO
				.getNuevaPortadaSize() > ConfiguracionAvatar.AVATAR_SIZE
				|| !ConfiguracionAvatar.AVATAR_TYPES
						.contains(editarPortadaVideojuegoDTO.getNuevaPortadaType())) {
			throw new AvatarExcepcion("La portada proporcionada no es valida");
		}

		if (editarPortadaVideojuegoDTO.getNuevaPortada() != null) {
			try (InputStream portadaStream = editarPortadaVideojuegoDTO
					.getNuevaPortada().getValueAs(InputStream.class)) {
				nuevaPortada = portadaStream.readAllBytes();

			} catch (IOException e) {
				nuevaPortada = convertirImagen.obtenerPortadaDefault();
			}

		}
		else {
			nuevaPortada = convertirImagen.obtenerPortadaDefault();
		}

		gestionVideojuegoDB.cambiarPortadaVideojuego(
				editarPortadaVideojuegoDTO.getIdVideojuego(), nuevaPortada);
	}

	public void editarVideojuego(EditarVideojuegoRequestDTO editarVideojuegoDTO)
			throws DatosInvalidos, ErrorActualizarRegistro {

		if (!editarVideojuegoDTO.datosValidos()) {
			throw new DatosInvalidos("Los datos proporcionados no son validos");
		}

		gestionVideojuegoDB.editarVideojuego(editarVideojuegoDTO);

	}

	public ListaVideojuegosDTO obtenerVideojuegos(Integer idUsuarioEmpresa)
			throws ErrorConsultaDB {

		UsuarioEmpresaDB usuarioEmpresaDB = new UsuarioEmpresaDB();
		Integer idEmpresa = usuarioEmpresaDB
				.obtenerIdEmpresaPorIdUsuario(idUsuarioEmpresa);

		return gestionVideojuegoDB.obtenerListaVideojuegos(idEmpresa);
	}

	public EditarVideojuegoResponseDTO obtenerDatosVideojuego(String idVideojuego)
			throws ErrorConsultaDB, DatosInvalidos {

		try {
			Integer videojuegoId = idVideojuego != null
					? Integer.parseInt(idVideojuego)
					: -1;

			if (videojuegoId <= 0) {
				throw new DatosInvalidos("El ID del videojuego no es valido");
			}

			return gestionVideojuegoDB.obtenerDatosVideojuegoPorId(videojuegoId);
		} catch (ErrorConsultaDB e) {
			throw e;
		} catch (NumberFormatException e) {
			throw new DatosInvalidos("El ID del videojuego no es valido");
		}
	}

}
