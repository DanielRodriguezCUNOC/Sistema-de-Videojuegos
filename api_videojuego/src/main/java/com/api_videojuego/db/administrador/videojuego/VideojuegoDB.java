package com.api_videojuego.db.administrador.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorEliminarRegistro;

public class VideojuegoDB {

	public void cambiarEstadoVideojuego(Integer idVideojuego, boolean estado,
			Connection conn) throws ErrorActualizarRegistro {

		String query = "UPDATE videojuego SET estado = ? WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setBoolean(1, estado);
			ps.setInt(2, idVideojuego);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al cambiar el estado del videojuego desde la tabla videojuego."
							+ e.getMessage());
		}

	}

	public void eliminarVideojuego(Integer idVideojuego, Connection conn)
			throws ErrorEliminarRegistro {
		String query = "DELETE FROM videojuego WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);
			ps.executeUpdate();
		} catch (

		SQLException e) {
			throw new ErrorEliminarRegistro(
					"Error al eliminar el videojuego de la tabla videojuego."
							+ e.getMessage());
		}
	}
}