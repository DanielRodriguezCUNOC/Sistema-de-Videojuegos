package com.api_videojuego.db.gamer.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class UsuarioGamerDB {

	public String obtenerGamerPorId(Integer idUsuario) throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT ug.nickname " + "FROM usuario_gamer AS ug "
				+ "WHERE ug.id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String nickname = rs.getString("nickname");

				return nickname;
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
				return rs.getBytes("avatar");
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener el avatar del usuario en la BD");
		}

		return null;
	}

}
