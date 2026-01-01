package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorConsultaDB;
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

	public Integer obtenerIdEmpresaPorIdVidejuego(Integer idVideojuego,
			Connection conn) throws ErrorConsultaDB {

		String query = "SELECT id_empresa FROM videojuego_desarrolladora WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);

			var rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("id_empresa");
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"No se pudo obtener el id de la empresa desde la tabla videojuego_desarrolladora");
		}
	}

	public Integer idEmpresaPorIdVidejuego(Integer idVideojuego)
			throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT id_empresa FROM videojuego_desarrolladora WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);

			var rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("id_empresa");
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"No se pudo obtener el id de la empresa desde la tabla videojuego_desarrolladora"
							+ e.getMessage());

		}
	}

}
