package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.api_videojuego.excepciones.ErrorInsertarDB;

public class SolicitudCategoriaDB {

	public Integer generarSolicitudCategoria(Integer idVideojuego,
			String idUsuario, List<String> categorias, Connection conn)
			throws ErrorInsertarDB {

		String sql = "INSERT INTO solicitud_categoria (id_usuario, id_videojuego, categoria, estado) VALUES (?, ?, ?, 'pendiente')";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {

			int registrosInsertados = 0;

			for (String categoria : categorias) {
				stmt.setInt(1, Integer.parseInt(idUsuario));
				stmt.setInt(2, idVideojuego);
				stmt.setString(3, categoria);

				int resultado = stmt.executeUpdate();

				if (resultado > 0) {
					registrosInsertados++;
				}
			}

			return registrosInsertados;

		} catch (SQLException e) {
			throw new ErrorInsertarDB("Error al generar solicitudes de categoría: ");
		}
	}

}
