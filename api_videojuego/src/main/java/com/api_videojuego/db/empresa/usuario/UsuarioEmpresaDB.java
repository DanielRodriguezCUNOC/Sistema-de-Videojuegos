package com.api_videojuego.db.empresa.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
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

	public String[] obtenerUsuarioEmpresaPorId(Integer idUsuario)
			throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT ue.nombre_completo, e.nombre_empresa "
				+ "FROM usuario_empresa AS ue "
				+ "JOIN empresa_desarrolladora AS e ON ue.id_empresa = e.id_empresa "
				+ "WHERE ue.id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String[] datosUsuario = new String[2];
				datosUsuario[0] = rs.getString("nombre_completo");
				datosUsuario[1] = rs.getString("nombre_empresa");

				return datosUsuario;
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener los datos del usuario en la BD");
		}

		return null;
	}

	public byte[] obtenerAvatarPorId(Integer idUsuario) throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT avatar FROM usuario WHERE id_usuario = ?";

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
