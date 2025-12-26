package com.api_videojuego.services.usuario.response;

import com.api_videojuego.db.administrador.usuario.UsuarioAdministradorDB;
import com.api_videojuego.db.empresa.usuario.UsuarioEmpresaDB;
import com.api_videojuego.db.gamer.usuario.UsuarioGamerDB;
import com.api_videojuego.dto.usuario.reponse.UsuarioAdminResponseDTO;
import com.api_videojuego.dto.usuario.reponse.UsuarioEmpresaResponseDTO;
import com.api_videojuego.dto.usuario.reponse.UsuarioGamerResponseDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ExcepcionInesperada;
import com.api_videojuego.utils.ConvertirImagen;

public class UsuarioResponseService {

	public UsuarioAdminResponseDTO usuarioAdminResponseDTO(Integer idUsuario)
			throws ErrorConsultaDB, ExcepcionInesperada {

		UsuarioAdministradorDB usuarioAdministradorDB = new UsuarioAdministradorDB();

		try {

			// * Obtener los datos del usuario administrador */
			String nombreCompleto = null;
			nombreCompleto = usuarioAdministradorDB
					.obtenerAdministradorPorId(idUsuario);
			byte[] avatar = usuarioAdministradorDB.obtenerAvatarPorId(idUsuario);

			// *Convertimos el arreglo de bytes a base64 y creamos el response */
			String avatarBase64 = convertirImagenBase64(
					(avatar != null) ? avatar : null);

			return new UsuarioAdminResponseDTO(nombreCompleto, avatarBase64);
		} catch (ErrorConsultaDB e) {
			throw e;
		}

	}

	public UsuarioEmpresaResponseDTO usuarioEmpresaResponseDTO(Integer idUsuario)
			throws ErrorConsultaDB, ExcepcionInesperada {
		UsuarioEmpresaDB usuarioEmpresaDB = new UsuarioEmpresaDB();

		try {
			// * Obtener los datos del usuario de la empresa */
			String datosUsuario[] = null;
			datosUsuario = usuarioEmpresaDB.obtenerUsuarioEmpresaPorId(idUsuario);
			byte[] avatar = usuarioEmpresaDB.obtenerAvatarPorId(idUsuario);

			// *Convertimos el arreglo de bytes a base64 y creamos el response */
			String avatarBase64 = convertirImagenBase64(
					(avatar != null) ? avatar : null);
			return new UsuarioEmpresaResponseDTO(datosUsuario[0], avatarBase64,
					datosUsuario[1]);

		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

	public UsuarioGamerResponseDTO usuarioGamerResponseDTO(Integer idUsuario)
			throws ErrorConsultaDB, ExcepcionInesperada {

		UsuarioGamerDB usuarioGamerDB = new UsuarioGamerDB();

		try {
			// * Obtenemos los datos del usuario gamer */
			String nickname;
			nickname = usuarioGamerDB.obtenerGamerPorId(idUsuario);
			byte[] avatar = usuarioGamerDB.obtenerAvatarPorId(idUsuario);

			// *Convertimos el arreglo de bytes a base64 y creamos el response */
			String avatarBase64 = convertirImagenBase64(
					(avatar != null) ? avatar : null);
			return new UsuarioGamerResponseDTO(nickname, avatarBase64);
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

	private String convertirImagenBase64(byte[] imagenBytes)
			throws ExcepcionInesperada {
		ConvertirImagen convertirImagen = new ConvertirImagen();

		return convertirImagen.convertirImagenBase64(imagenBytes);
	}
}
