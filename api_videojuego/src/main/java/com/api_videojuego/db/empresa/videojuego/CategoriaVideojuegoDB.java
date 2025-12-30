package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CategoriaVideojuegoDB {

	public void agregarRegistro(Integer idCategoria, Integer idVideojuego)
			throws ErrorInsertarDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "INSERT INTO categoria_videojuego (id_categoria, id_videojuego) VALUES (?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idCategoria);
			ps.setInt(2, idVideojuego);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"No se pudo crear el registro en la tabla categoria_videojuego.");
		}
	}

	public void nuevoRegistro(Integer idCategoria, Integer idVideojuego,
			Connection conn) throws ErrorInsertarDB {

		String query = "INSERT INTO categoria_videojuego (id_categoria, id_videojuego) VALUES (?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idCategoria);
			ps.setInt(2, idVideojuego);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"No se pudo crear el registro en la tabla categoria_videojuego."
							+ e.getMessage());
		}
	}

}
