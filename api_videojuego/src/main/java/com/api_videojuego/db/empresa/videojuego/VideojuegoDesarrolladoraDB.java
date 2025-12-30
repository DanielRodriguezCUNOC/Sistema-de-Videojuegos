package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.api_videojuego.excepciones.ErrorEliminarRegistro;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class VideojuegoDesarrolladoraDB {

	public Integer registrarVideojuegoDesarrolladora(Integer idEmpresa,
			Integer idVideojuego, Connection conn) throws ErrorInsertarDB {

		String query = "INSERT INTO videojuego_desarrolladora (id_empresa, id_videojuego) VALUES (?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idEmpresa);
			ps.setInt(2, idVideojuego);

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

	public void eliminarRegistro(Integer idVideojuego, Connection conn)
			throws ErrorEliminarRegistro {

		String query = "DELETE FROM videojuego_desarrolladora WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorEliminarRegistro(
					"Error al eliminar la relación videojuego-desarrolladora: "
							+ e.getMessage());
		}
	}

}
