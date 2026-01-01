package com.api_videojuego.db.gamer.usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class UsuarioGamerDB {

	public String obtenerGamerPorId(Integer idUsuario) throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT ug.nickname " + "FROM usuario_gamer AS ug "
				+ "WHERE ug.id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String nickname = rs.getString("nickname");

				return nickname;
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener los datos del usuario en la BD");
		}

		return null;
	}

	public byte[] obtenerAvatarPorId(Integer idUsuario) throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT avatar " + "FROM usuario " + "WHERE id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getBytes("avatar");
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener el avatar del usuario en la BD");
		}

		return null;
	}

	/*
	 * Obtiene la edad actual del usuario basada en su fecha de nacimiento
	 */
	public Integer obtenerEdadUsuario(Integer idUsuario) throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT fecha_nacimiento FROM usuario WHERE id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Date fechaNacimiento = rs.getDate("fecha_nacimiento");
				LocalDate fechaNac = fechaNacimiento.toLocalDate();
				LocalDate fechaActual = LocalDate.now();

				/*
				 * Calcular la diferencia en años entre la fecha de nacimiento y la
				 * fecha actual esto devuelve un entero
				 */
				return Period.between(fechaNac, fechaActual).getYears();
			}
			else {
				throw new ErrorConsultaDB("Usuario no encontrado con ID: " + idUsuario);
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener la edad del usuario: " + e.getMessage());
		}
	}

}
