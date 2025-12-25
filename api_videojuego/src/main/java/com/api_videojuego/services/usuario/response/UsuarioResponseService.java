package com.api_videojuego.services.usuario.response;

import com.api_videojuego.db.administrador.usuario.UsuarioAdministradorDB;
import com.api_videojuego.db.empresa.usuario.UsuarioEmpresaDB;
import com.api_videojuego.db.gamer.usuario.UsuarioGamerDB;
import com.api_videojuego.dto.usuario.reponse.UsuarioAdminResponseDTO;
import com.api_videojuego.dto.usuario.reponse.UsuarioEmpresaResponseDTO;
import com.api_videojuego.dto.usuario.reponse.UsuarioGamerResponseDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class UsuarioResponseService {

	public UsuarioAdminResponseDTO usuarioAdminResponseDTO(Integer idUsuario)
			throws ErrorConsultaDB {

		UsuarioAdministradorDB usuarioAdministradorDB = new UsuarioAdministradorDB();

		try {

			// * Validamos el id del usuario */
			if (idUsuario == null || idUsuario <= 0) {
				throw new ErrorConsultaDB("El ID de usuario no es válido.");
			}

			// * Obtener los datos del usuario administrador */
			return usuarioAdministradorDB.obtenerAdministradorPorId(idUsuario);
		} catch (ErrorConsultaDB e) {
			throw e;
		}

	}

	public UsuarioEmpresaResponseDTO usuarioEmpresaResponseDTO(Integer idUsuario)
			throws ErrorConsultaDB {
		UsuarioEmpresaDB usuarioEmpresaDB = new UsuarioEmpresaDB();

		try {
			// * Obtener los datos del usuario de la empresa */
			return usuarioEmpresaDB.obtenerUsuarioEmpresaPorId(idUsuario);
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}

	public UsuarioGamerResponseDTO usuarioGamerResponseDTO(Integer idUsuario)
			throws ErrorConsultaDB {

		UsuarioGamerDB usuarioGamerDB = new UsuarioGamerDB();

		try {
			// * Obtenemos los datos del usuario gamer */
			return usuarioGamerDB.obtenerGamerPorId(idUsuario);
		} catch (ErrorConsultaDB e) {
			throw e;
		}
	}
}
