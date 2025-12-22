package com.api_videojuego.db.administrador.comision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.math.BigDecimal;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.administrador.comision.EditarComisionGlobalDTO;
import com.api_videojuego.dto.administrador.comision.ObtenerComisionGlobalDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class ComisionGlobalDB {

	public void actualizarComisionGlobal(EditarComisionGlobalDTO comision,
			LocalDate fechaActual, Connection conn) throws Exception {

		String query = "UPDATE comision_global SET comision = ?, fecha_creacion = ? WHERE id = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setDouble(1, comision.getComision().doubleValue());
			ps.setObject(2, fechaActual);
			ps.setInt(3, comision.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al actualizar la comision global: " + e.getMessage());
		}
	}

	public boolean existeComisionGlobal(Integer id) throws Exception {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT COUNT(*) AS count FROM comision_global WHERE id = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt("count");
				return count > 0;
			}
		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al verificar si la comision especifica ya existe: "
							+ e.getMessage());
		}
		return false;
	}

	public ObtenerComisionGlobalDTO obtenerComisionGlobal() throws Exception {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT id, comision, fecha_creacion FROM comision_global LIMIT 1";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ObtenerComisionGlobalDTO comision = new ObtenerComisionGlobalDTO();
				comision.setId(rs.getInt("id"));
				comision.setComision(BigDecimal.valueOf(rs.getDouble("comision")));
				comision
						.setFechaCreacion(rs.getObject("fecha_creacion", LocalDate.class));
				return comision;
			}
		} catch (SQLException e) {
			throw new ErrorConsultaDB("Error al obtener la comision global");
		}
		return null;
	}

}
