package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.videojuego.CalificacionVideojuegoRequestDTO;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CalificacionVideojuegoDB {

	public Double obtenerCalificacionPromedioPorIdVideojuego(Integer idVideojuego,
			Connection conn) {

		String query = "SELECT AVG(calificacion) AS calificacion_promedio "
				+ "FROM calificacion_videojuego " + "WHERE id_videojuego = ?";

		try (var ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Double calificacionPromedio = rs.getDouble("calificacion_promedio");
				return calificacionPromedio;
			}
			else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void registrarCalificacion(
			CalificacionVideojuegoRequestDTO calificacionDTO) throws ErrorInsertarDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "INSERT INTO calificacion_videojuego "
				+ "( id_usuario, id_videojuego, calificacion, fecha_calificacion) "
				+ "VALUES (?, ?, ?, ?)";

		try (var ps = conn.prepareStatement(query)) {
			ps.setInt(1, calificacionDTO.getIdUsuario());
			ps.setInt(2, calificacionDTO.getIdVideojuego());
			ps.setInt(3, calificacionDTO.getCalificacion());
			ps.setDate(4, Date.valueOf(LocalDate.now()));

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al registrar la calificación en la base de datos: "
							+ e.getMessage());
		}

	}
}
