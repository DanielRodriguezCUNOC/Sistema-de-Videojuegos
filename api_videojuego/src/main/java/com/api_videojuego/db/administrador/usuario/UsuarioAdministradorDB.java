package com.api_videojuego.db.administrador.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.usuario.reponse.UsuarioAdminResponseDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class UsuarioAdministradorDB {

	public UsuarioAdminResponseDTO obtenerAdministradorPorId(Integer idUsuario)
			throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT ads.nombre_completo, u.avatar "
				+ "FROM administrador_sistema AS ads "
				+ "JOIN usuario AS u ON ads.id_usuario = u.id_usuario "
				+ "WHERE ads.id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				UsuarioAdminResponseDTO adminDTO = new UsuarioAdminResponseDTO();
				adminDTO.setNombreCompleto(rs.getString("nombre_completo"));
				adminDTO.setAvatar(rs.getBytes("avatar"));

				return adminDTO;
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener los datos del usuario en la BD");
		}

		return null;
	}

}
