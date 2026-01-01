package com.api_videojuego.db.gamer.grupo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.gamer.grupo.GrupoRequestDTO;
import com.api_videojuego.excepciones.DatosInvalidos;
import com.api_videojuego.excepciones.DatoYaExiste;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class GrupoDB {

	public void registrarGrupo(GrupoRequestDTO grupo)
			throws DatosInvalidos, DatoYaExiste, ErrorInsertarDB {

		// Validar los datos del grupo
		if (!grupo.isValid()) {
			throw new DatosInvalidos("Los datos del grupo son inválidos");
		}

		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		String sql = "INSERT INTO grupo (id_creador, nombre_grupo, fecha_creacion, estado) VALUES (?, ?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, grupo.getIdCreador());
			ps.setString(2, grupo.getNombreGrupo());
			ps.setDate(3, Date.valueOf(LocalDate.now()));
			ps.setInt(4, 1);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected == 0) {
				throw new ErrorInsertarDB("No se pudo crear el grupo");
			}

		} catch (SQLException e) {

			throw new ErrorInsertarDB("Error al crear el grupo: " + e.getMessage());
		}
	}

	public boolean grupoExiste(String nombreGrupo) throws ErrorInsertarDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String sql = "SELECT COUNT(*) FROM grupo WHERE nombre_grupo = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, nombreGrupo);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al verificar existencia del grupo: " + e.getMessage());
		}
		return false;
	}

}
