package com.api_videojuego.db.administrador.empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CrearEmpresaDB {

	public boolean existeEmpresaPorNombre(String nombreEmpresa)
			throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT COUNT(*) AS count FROM empresa_desarrolladora WHERE nombre_empresa = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, nombreEmpresa);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int total = rs.getInt("count");
				return total > 0;
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB("Ya existe una empresa con este nombre");
		}
		return false;
	}

	public Integer registrarEmpresa(String nombreEmpresa, String descripcion,
			String estadoComentario) throws ErrorInsertarDB, ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "INSERT INTO empresa_desarrolladora (nombre_empresa, descripcion, estado_comentarios, estado) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, nombreEmpresa);
			ps.setString(2, descripcion);
			ps.setInt(3, Boolean.parseBoolean(estadoComentario) ? 1 : 0);
			ps.setString(4, "activa");

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected == 0) {
				throw new ErrorInsertarDB("No se pudo registrar la empresa.");
			}
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
				else {
					throw new ErrorConsultaDB(
							"No se pudo obtener el ID de la empresa registrada.");
				}
			}
		} catch (SQLException e) {

			throw new ErrorInsertarDB(
					"Error al registrar la empresa en la base de datos");
		}
	}

}
