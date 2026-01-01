package com.api_videojuego.db.administrador.clasificacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class ClasificacionDB {

	/**
	 * Obtiene la edad mínima requerida para un videojuego específico
	 */
	public String obtenerEdadMinimaVideojuego(Integer idVideojuego)
			throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT c.edad " + "FROM clasificacion c "
				+ "INNER JOIN clasificacion_videojuego cv ON c.id_clasificacion = cv.id_clasificacion "
				+ "WHERE cv.id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString("edad");
			}
			else {
				throw new ErrorConsultaDB(
						"No se encontró clasificación para el videojuego con ID: "
								+ idVideojuego);
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al consultar la edad mínima del videojuego: "
							+ e.getMessage());
		}
	}

}
