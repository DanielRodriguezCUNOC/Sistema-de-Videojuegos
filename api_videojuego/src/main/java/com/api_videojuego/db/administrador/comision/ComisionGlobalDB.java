package com.api_videojuego.db.administrador.comision;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.math.BigDecimal;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.administrador.comision.EditarComisionGlobalDTO;
import com.api_videojuego.dto.administrador.comision.ObtenerComisionGlobalDTO;
import com.api_videojuego.dto.administrador.comision.ComisionEspecificaResponseDTO;
import com.api_videojuego.dto.administrador.comision.ListaComisionEspecificaDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class ComisionGlobalDB {

	public boolean existeComisionGlobal(Integer id)
			throws ErrorActualizarRegistro {
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

	public ObtenerComisionGlobalDTO obtenerComisionGlobal()
			throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT id, comision, fecha_creacion FROM comision_global LIMIT 1";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ObtenerComisionGlobalDTO comision = new ObtenerComisionGlobalDTO();
				comision.setId(rs.getInt("id"));
				comision.setComision(BigDecimal.valueOf(rs.getDouble("comision")));
				comision.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
				return comision;
			}
		} catch (SQLException e) {
			throw new ErrorConsultaDB("Error al obtener la comision global");
		}
		return null;
	}

	public void actualizarComisionGlobal(EditarComisionGlobalDTO comision,
			LocalDate fechaActual) throws ErrorActualizarRegistro, ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		ComisionEspecificaDB comisionEspecificaDB = new ComisionEspecificaDB();

		try {
			conn.setAutoCommit(false);

			String updateGlobalQuery = "UPDATE comision_global SET comision = ?, fecha_creacion = ? WHERE id = ?";
			try (PreparedStatement psGlobal = conn
					.prepareStatement(updateGlobalQuery)) {
				psGlobal.setDouble(1, comision.getComision().doubleValue());
				psGlobal.setDate(2, Date.valueOf(fechaActual));
				psGlobal.setInt(3, comision.getId());
				psGlobal.executeUpdate();
			}

			ListaComisionEspecificaDTO comisiones = comisionEspecificaDB
					.listaComisionEspecifica(conn);

			// * Actualizar comision especifica si es requerido */
			String updateEspecificaQuery = "UPDATE comision_especifica SET comision_especifica = ?, fecha_actualizacion = ? WHERE id = ?";
			try (PreparedStatement psEspecifica = conn
					.prepareStatement(updateEspecificaQuery)) {

				for (ComisionEspecificaResponseDTO comisionEsp : comisiones
						.getComisiones()) {
					if (comisionEsp.getComisionEspecifica()
							.compareTo(comision.getComision()) > 0) {
						psEspecifica.setBigDecimal(1, comision.getComision());
						psEspecifica.setDate(2, Date.valueOf(fechaActual));
						psEspecifica.setInt(3, comisionEsp.getId());
						psEspecifica.executeUpdate();
					}
				}
			}

			conn.commit();

		} catch (SQLException e) {
			try {
				System.err.println("Error en transacción, ejecutando rollback");
				conn.rollback();
			} catch (SQLException rollbackEx) {
				throw new ErrorActualizarRegistro(
						"Error al ejecutar rollback: " + rollbackEx.getMessage());
			}
			throw new ErrorActualizarRegistro(
					"Error al actualizar comisiones: " + e.getMessage());
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println(
						"Warning: No se pudo restaurar autoCommit: " + e.getMessage());
			}
		}
	}

}
