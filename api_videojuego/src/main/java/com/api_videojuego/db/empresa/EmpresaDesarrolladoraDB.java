package com.api_videojuego.db.empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.db.empresa.videojuego.VideojuegoDesarrolladoraDB;
import com.api_videojuego.dto.empresa.comentarios.EditarEstadoEmpresaRequestDTO;
import com.api_videojuego.dto.empresa.comentarios.EditarEstadoEmpresaResponseDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class EmpresaDesarrolladoraDB {

	public void cambiarEstadoComentarios(
			EditarEstadoEmpresaRequestDTO editarEstadoEmpresaRequestDTO)
			throws ErrorActualizarRegistro {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE empresa_desarrolladora SET estado_comentarios = ? WHERE id_empresa = ?";

		try {
			conn.setAutoCommit(false);
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setBoolean(1, editarEstadoEmpresaRequestDTO.getEstadoComentarios());
				ps.setInt(2, editarEstadoEmpresaRequestDTO.getIdEmpresa());
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {

					/*
					 * Cambiar estado de comentarios de todos los videojuegos de la
					 * empresa
					 */
					VideojuegoDesarrolladoraDB videojuegoDB = new VideojuegoDesarrolladoraDB();
					videojuegoDB.cambiarEstadoComentariosVideojuego(
							editarEstadoEmpresaRequestDTO.getIdEmpresa(),
							editarEstadoEmpresaRequestDTO.getEstadoComentarios(), conn);

					conn.commit();

				}
				else {
					throw new ErrorActualizarRegistro(
							"No se pudo actualizar el estado de comentarios generales para la empresa");
				}
			} catch (SQLException e) {
				throw new ErrorActualizarRegistro(
						"Error al cambiar el estado de comentarios generales desde la DB");
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				throw new ErrorActualizarRegistro(
						"Error al realizar rollback de la transacción: " + ex.getMessage());
			}
			throw new ErrorActualizarRegistro(
					"Error al registrar el pago del videojuego: " + e.getMessage());
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new ErrorActualizarRegistro(
						"Error al restablecer el modo de autocommit: " + e.getMessage());
			}
		}
	}

	public EditarEstadoEmpresaResponseDTO obtenerEstadoComentariosEmpresa(
			Integer idEmpresa) throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		EditarEstadoEmpresaResponseDTO estado = null;

		String query = "SELECT id_empresa, estado_comentarios, nombre_empresa FROM empresa_desarrolladora WHERE id_empresa = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idEmpresa);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				estado = new EditarEstadoEmpresaResponseDTO(rs.getInt("id_empresa"),
						rs.getString("nombre_empresa"),
						rs.getBoolean("estado_comentarios"));

			}
			return estado;

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al consultar el etado de los comentarios para la empresa");
		}
	}
}
