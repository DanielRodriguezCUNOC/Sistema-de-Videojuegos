package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEliminarRegistro;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class ClasificacionVideojuegoDB {

	public Integer obtenerIdClasificacionPorNombre(String nombreClasificacion,
			Connection conn) throws ErrorConsultaDB {

		String query = "SELECT id_clasificacion FROM clasificacion WHERE clasificacion = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, nombreClasificacion);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id_clasificacion");
				}
				else {
					throw new ErrorConsultaDB(
							"No se encontró la clasificación: " + nombreClasificacion);
				}
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener el ID de la clasificación: " + e.getMessage());
		}
	}

	public Integer registrarClasificacionVideojuego(Integer idVideojuego,
			String clasificacion, Connection conn) throws ErrorInsertarDB {

		String query = "INSERT INTO clasificacion_videojuego (id_clasificacion, id_videojuego) VALUES (?, ?)";

		try {
			conn.setAutoCommit(false);

			Integer idClasificacion;
			try {
				idClasificacion = obtenerIdClasificacionPorNombre(clasificacion, conn);
			} catch (ErrorConsultaDB e) {
				conn.rollback();
				throw new ErrorInsertarDB(
						"No se pudo obtener el ID de la clasificación: " + e.getMessage());
			}

			try (PreparedStatement stmt = conn.prepareStatement(query)) {

				stmt.setInt(1, idClasificacion);
				stmt.setInt(2, idVideojuego);

				int filasInsertadas = stmt.executeUpdate();

				if (filasInsertadas > 0) {
					conn.commit();
					return idClasificacion;
				}
				else {
					conn.rollback();
					throw new ErrorInsertarDB(
							"No se pudo registrar la clasificación del videojuego.");
				}

			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
			throw new ErrorInsertarDB(
					"Error al registrar la clasificación del videojuego: "
							+ e.getMessage());
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
		}
	}

	public void eliminarRegistro(Integer idVideojuego)
			throws ErrorEliminarRegistro {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "DELETE FROM clasificacion_videojuego WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idVideojuego);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorEliminarRegistro(
					"Error al eliminar las clasificaciones del videojuego con ID");
		}
	}

}