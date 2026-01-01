package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class VideojuegoCompradoDB {

	public void registrarVideojuegoComprado(Integer idUsuario,
			Integer idVideojuego) throws ErrorInsertarDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "INSERT INTO videojuego_comprado (id_usuario, id_videojuego, instalado, fecha_compra) "
				+ "VALUES (?, ?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idUsuario);
			ps.setInt(2, idVideojuego);
			ps.setBoolean(3, false); // * No instalado por defecto */
			ps.setDate(4, Date.valueOf(LocalDate.now()));
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al registrar el videojuego comprado: " + e.getMessage());
		}
	}
}
