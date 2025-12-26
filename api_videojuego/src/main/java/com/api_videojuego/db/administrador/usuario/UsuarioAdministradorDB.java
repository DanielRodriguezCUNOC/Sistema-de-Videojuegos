package com.api_videojuego.db.administrador.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class UsuarioAdministradorDB {

	public String obtenerAdministradorPorId(Integer idUsuario)
			throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT ads.nombre_completo "
				+ "FROM administrador_sistema AS ads " + "WHERE ads.id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String nombreCompleto = rs.getString("nombre_completo");

				return nombreCompleto;
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener los datos del usuario en la BD");
		}

		return null;
	}

	public byte[] obtenerAvatarPorId(Integer idUsuario) throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT avatar " + "FROM usuario " + "WHERE id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				byte[] avatar = rs.getBytes("avatar");

				return avatar;
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener el avatar del usuario en la BD");
		}

		return null;
	}

}
