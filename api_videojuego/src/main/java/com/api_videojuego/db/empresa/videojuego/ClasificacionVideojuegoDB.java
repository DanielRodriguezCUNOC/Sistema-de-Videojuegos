package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class ClasificacionVideojuegoDB {

	public Integer obtenerIdClasificacionPorNombre(String nombreClasificacion,
			Connection conn) throws ErrorConsultaDB {

		String sql = "SELECT id_clasificacion FROM clasificacion WHERE clasificacion = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, nombreClasificacion);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("id_clasificacion");
			}
			else {
				throw new ErrorConsultaDB(
						"No se encontró la clasificación: " + nombreClasificacion);
			}

		} catch (Exception e) {
			throw new ErrorConsultaDB(
					"Error al obtener el ID de la clasificación: " + e.getMessage());
		}

	}

	public Integer registrarClasificacionVideojuego(Integer idVideojuego,
			String clasificacion, Connection conn) throws ErrorInsertarDB {

		String sql = "INSERT INTO clasificacion_videojuego (id_videojuego, clasificacion) VALUES (?, ?)";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {

			conn.setAutoCommit(false);
			int idClasificacion;

			try {
				idClasificacion = obtenerIdClasificacionPorNombre(clasificacion, conn);
			} catch (ErrorConsultaDB e) {
				throw new ErrorInsertarDB(
						"No se pudo obtener el ID de la clasificación: " + e.getMessage());
			}

			stmt.setInt(1, idVideojuego);
			stmt.setInt(2, idClasificacion);

			int filasInsertadas = stmt.executeUpdate();

			if (filasInsertadas > 0 && idClasificacion > 0) {
				conn.commit();
			}
			else {
				conn.rollback();
				throw new ErrorInsertarDB(
						"No se pudo registrar la clasificación del videojuego.");
			}
			return filasInsertadas;

		} catch (Exception e) {
			throw new ErrorInsertarDB(
					"Error al registrar la clasificación del videojuego");
		}

	}

}
