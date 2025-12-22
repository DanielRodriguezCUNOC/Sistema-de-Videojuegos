package com.api_videojuego.db.empresa.videojuego;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CrearVideojuegoDB {

	public boolean existeVideojuegoPorTitulo(String titulo) throws Exception {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT COUNT(*) AS cantidad FROM videojuego WHERE titulo = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, titulo);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int cantidad = rs.getInt("cantidad");
				return cantidad > 0;
			}

		} catch (Exception e) {
			throw new ErrorConsultaDB(
					"Error al verificar si el videojuego ya esta registrado");
		}

		return false;
	}

	public Integer crearVideojuego(String titulo, String descripcion,
			LocalDate fechaLanzamiento, BigDecimal precio, String recursosMinimos,
			boolean estado) throws Exception {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "INSERT INTO videojuego (titulo, descripcion, fecha_lanzamiento, precio, recursos_minimos, estado) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, titulo);
			ps.setString(2, descripcion);
			ps.setDate(3, Date.valueOf(fechaLanzamiento));
			ps.setBigDecimal(4, precio);
			ps.setString(5, recursosMinimos);
			ps.setBoolean(6, estado);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected == 0) {
				throw new ErrorInsertarDB(
						"No se pudo crear el videojuego en la base de datos");
			}
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
				else {
					throw new ErrorConsultaDB(
							"No se pudo obtener el ID del videojuego creado.");
				}
			}

		} catch (Exception e) {
			throw new ErrorInsertarDB(
					"Error al crear el videojuego en la base de datos");
		}
	}

}
