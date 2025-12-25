package com.api_videojuego.db.empresa.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.usuario.reponse.UsuarioEmpresaResponseDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class UsuarioEmpresaDB {

	public Integer obtenerIdEmpresaPorIdUsuario(Integer idUsuario)
			throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT id_empresa FROM usuario_empresa WHERE id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("id_empresa");
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al tratar de obtener el id de la empresa");
		}
		return null;
	}

	public UsuarioEmpresaResponseDTO obtenerUsuarioEmpresaPorId(Integer idUsuario)
			throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT ue.nombre_completo, u.avatar "
				+ "FROM usuario_empresa AS ue "
				+ "JOIN usuario AS u ON ue.id_usuario = u.id_usuario "
				+ "WHERE ue.id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				UsuarioEmpresaResponseDTO empresaDTO = new UsuarioEmpresaResponseDTO();
				empresaDTO.setNombreCompleto(rs.getString("nombre_completo"));
				empresaDTO.setAvatar(rs.getBytes("avatar"));

				return empresaDTO;
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener los datos del usuario en la BD");
		}

		return null;
	}
}
