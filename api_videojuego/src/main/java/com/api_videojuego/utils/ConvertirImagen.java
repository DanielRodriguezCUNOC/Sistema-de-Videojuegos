package com.api_videojuego.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import com.api_videojuego.excepciones.ExcepcionInesperada;

public class ConvertirImagen {

	// * Metodo utilizado para convertir a arreglo de bytes*/
	public byte[] obtenerAvatarDefault() throws ExcepcionInesperada {

		try (InputStream defaultImg = getClass()
				.getResourceAsStream("/images/default-avatar.png")) {
			return defaultImg.readAllBytes();

		} catch (IOException ex) {
			throw new ExcepcionInesperada(
					"No se han podido obtener la foto de perfil predeterminada");
		}

	}

	// * Funcion para convertir imagen a base64 */

	public String convertirImagenBase64(byte[] imagenBytes)
			throws ExcepcionInesperada {

		if (imagenBytes != null) {
			return Base64.getEncoder().encodeToString(imagenBytes);
		}
		else {
			try (InputStream defaultImg = getClass()
					.getResourceAsStream("/images/default-avatar.png")) {

				byte[] bytes = defaultImg.readAllBytes();

				return Base64.getEncoder().encodeToString(bytes);

			} catch (IOException ex) {
				throw new ExcepcionInesperada(
						"No se han podido obtener la foto de perfil predeterminada");
			}
		}
	}

}
