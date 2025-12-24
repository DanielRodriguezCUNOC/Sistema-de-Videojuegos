package com.api_videojuego.utils;

import java.io.IOException;
import java.io.InputStream;

import com.api_videojuego.excepciones.ExcepcionInesperada;

public class ConvertirImagen {

	// * Metodo utilizado para convertir a base 64 la foto */
	public byte[] obtenerAvatarDefault() throws ExcepcionInesperada {

		try (InputStream defaultImg = getClass()
				.getResourceAsStream("/images/default-avatar.png")) {
			return defaultImg.readAllBytes();

		} catch (IOException ex) {
			throw new ExcepcionInesperada(
					"No se han podido obtener la foto de perfil predeterminada");
		}

	}

}
