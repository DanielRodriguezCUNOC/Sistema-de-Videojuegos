package com.api_videojuego.db.administrador.comision;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.administrador.comision.ComisionEspecificaRequestDTO;
import com.api_videojuego.dto.administrador.comision.ComisionEspecificaResponseDTO;
import com.api_videojuego.dto.administrador.comision.EditarComisionEspecificaDTO;
import com.api_videojuego.dto.administrador.comision.ListaComisionEspecificaDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class ComisionEspecificaDB {

	public void actualizarComisionEspecifica(EditarComisionEspecificaDTO comision,
			LocalDate fechaActual) throws ErrorActualizarRegistro {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE comision_especifica SET comision_especifica = ?, fecha_creacion = ? WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setBigDecimal(1, comision.getComisionEspecifica());
			ps.setObject(2, fechaActual);
			ps.setInt(3, comision.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al actualizar la comision especifica: " + e.getMessage());
		}
	}

	public boolean existeComisionEspecifica(Integer idEmpresa)
			throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT COUNT(*) AS count FROM comision_especifica WHERE id_empresa = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idEmpresa);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt("count");
				return count > 0;
			}
		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al verificar si la comision especifica ya existe: "
							+ e.getMessage());
		}
		return false;
	}

	public ListaComisionEspecificaDTO listaComisionEspecifica()
			throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT ce.id, ce.comision_especifica, ed.nombre_empresa "
				+ "FROM comision_especifica ce "
				+ "JOIN empresa_desarrolladora ed ON ce.id_empresa = ed.id_empresa";

		ListaComisionEspecificaDTO comisiones = new ListaComisionEspecificaDTO();

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Integer id = rs.getInt("id");
				String nombreEmpresa = rs.getString("nombre_empresa");
				BigDecimal comisionEspecifica = rs.getBigDecimal("comision_especifica");
				Date fechaActualizacion = rs.getDate("fecha_actualizacion");
				comisiones.agregarComisionEspecifica(id, nombreEmpresa,
						comisionEspecifica, fechaActualizacion);
			}
			return comisiones;
		} catch (SQLException e) {
			throw new ErrorConsultaDB("Error al obtener las comisiones especificas");
		}

	}

	public void registrarComisionEspecifica(ComisionEspecificaRequestDTO comision,
			LocalDate fechaActual, Integer idEmpresa) throws ErrorInsertarDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "INSERT INTO comision_especifica (id_empresa, comision_especifica, fecha_actualizacion) "
				+ "VALUES (?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idEmpresa);
			ps.setBigDecimal(2, comision.getComisionEspecifica());
			ps.setObject(3, fechaActual);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorInsertarDB("Error al registrar la comision especifica");
		}
	}

	public Integer obtenerIdEmpresaporNombre(String nombreEmpresa)
			throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT id_empresa FROM empresa_desarrolladora WHERE nombre_empresa = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, nombreEmpresa);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("id_empresa");
			}
		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener el id de la empresa por nombre");
		}
		return null;
	}

	public void cambiarComisionEspecifica(ComisionEspecificaResponseDTO comision,
			LocalDate fechaActual, Connection conn) throws ErrorActualizarRegistro {

		String query = "UPDATE comision_especifica SET comision_especifica = ?, fecha_actualizacion = ? WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setBigDecimal(1, comision.getComisionEspecifica());
			ps.setObject(2, fechaActual);
			ps.setInt(3, comision.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al actualizar la comision especifica");
		}
	}

}
