package com.api_videojuego.db.gamer.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.usuario.reponse.UsuarioGamerResponseDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class UsuarioGamerDB {

	public UsuarioGamerResponseDTO obtenerGamerPorId(Integer idUsuario)
			throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT ug.nickname, u.avatar " + "FROM usuario_gamer AS ug "
				+ "JOIN usuario AS u ON ug.id_usuario = u.id_usuario "
				+ "WHERE ug.id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				UsuarioGamerResponseDTO gamerDTO = new UsuarioGamerResponseDTO();
				gamerDTO.setNickname(rs.getString("nickname"));
				gamerDTO.setAvatar(rs.getBytes("avatar"));

				return gamerDTO;
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener los datos del usuario en la BD");
		}

		return null;
	}

}
