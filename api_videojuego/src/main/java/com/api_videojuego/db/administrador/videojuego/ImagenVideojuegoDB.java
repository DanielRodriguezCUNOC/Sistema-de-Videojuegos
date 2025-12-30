package com.api_videojuego.db.administrador.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.api_videojuego.excepciones.ErrorEliminarRegistro;

public class ImagenVideojuegoDB {

	public void eliminarImagenesVideojuego(Integer idVideojuego, Connection conn)
			throws ErrorEliminarRegistro {
		String query = "DELETE FROM imagen_juego WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorEliminarRegistro(
					"No se pudieron eliminar las imágenes del videojuego"
							+ e.getMessage());

		}
	}
}
