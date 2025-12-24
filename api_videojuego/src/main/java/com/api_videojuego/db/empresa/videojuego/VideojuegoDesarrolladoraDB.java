package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.api_videojuego.excepciones.ErrorInsertarDB;

public class VideojuegoDesarrolladoraDB {

	public Integer registrarVideojuegoDesarrolladora(Integer idVideojuego,
			Connection conn) throws ErrorInsertarDB {

		String query = "INSERT INTO videojuego_desarrolladora (id_videojuego) VALUES (?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);

			int filasAfectadas = ps.executeUpdate();

			if (filasAfectadas > 0) {
				return filasAfectadas;
			}
			else {
				throw new ErrorInsertarDB(
						"No se pudo registrar la relacion videojuego-desarrolladora");
			}

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al registrar la relacion videojuego-desarrolladora: "
							+ e.getMessage());
		}
	}

}
