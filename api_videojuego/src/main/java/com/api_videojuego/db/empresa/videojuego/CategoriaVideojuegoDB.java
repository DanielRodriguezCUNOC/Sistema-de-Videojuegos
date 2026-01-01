package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorConsultaDB;
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

	public List<Integer> obtenerCategoriasPorVideojuego(Integer idVideojuego,
			Connection conn) throws ErrorConsultaDB {

		String query = "SELECT id_categoria FROM categoria_videojuego WHERE id_videojuego = ?";

		List<Integer> categorias = new java.util.ArrayList<>();

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				categorias.add(rs.getInt("id_categoria"));
			}

			return categorias;
		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al consultar las categorías del videojuego");
		}
	}

}
