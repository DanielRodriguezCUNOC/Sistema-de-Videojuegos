package com.api_videojuego.db.gamer.grupo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.gamer.grupo.EditarEstadoGrupoRquestDTO;
import com.api_videojuego.dto.gamer.grupo.EditarGrupoRequestDTO;
import com.api_videojuego.dto.gamer.grupo.GrupoRequestDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class GrupoDB {

	public void registrarGrupo(GrupoRequestDTO grupo) throws ErrorInsertarDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		try {
			conn.setAutoCommit(false);

			String query = "INSERT INTO grupo (id_creador, nombre_grupo, fecha_creacion, estado) VALUES (?, ?, ?, ?)";
			int idGrupo = 0;

			try (PreparedStatement psGrupo = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS)) {
				psGrupo.setInt(1, grupo.getIdCreador());
				psGrupo.setString(2, grupo.getNombreGrupo());
				psGrupo.setDate(3, Date.valueOf(LocalDate.now()));
				psGrupo.setInt(4, 1);

				int rowsAffected = psGrupo.executeUpdate();

				if (rowsAffected == 0) {
					throw new ErrorInsertarDB("No se pudo crear el grupo");
				}

				try (ResultSet generatedKeys = psGrupo.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						idGrupo = generatedKeys.getInt(1);
					}
					else {
						throw new ErrorInsertarDB(
								"No se pudo obtener el ID del grupo creado");
					}
				}
			}

			String integrante = "INSERT INTO integrante_grupo (id_integrante, id_grupo, tipo_integrante, fecha_creacion, estado) VALUES (?, ?, ?, ?, ?)";

			try (PreparedStatement psIntegrante = conn.prepareStatement(integrante)) {
				psIntegrante.setInt(1, grupo.getIdCreador());
				psIntegrante.setInt(2, idGrupo);
				psIntegrante.setString(3, "propietario");
				psIntegrante.setDate(4, Date.valueOf(LocalDate.now()));
				psIntegrante.setInt(5, 1);

				int rowsAffectedIntegrante = psIntegrante.executeUpdate();

				if (rowsAffectedIntegrante == 0) {
					throw new ErrorInsertarDB(
							"No se pudo agregar el creador como integrante del grupo");
				}
			}

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException rollbackEx) {
				throw new ErrorInsertarDB(
						"Error al revertir transacción: " + rollbackEx.getMessage());
			}
			throw new ErrorInsertarDB("Error al crear el grupo: " + e.getMessage());
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new ErrorInsertarDB(
						"Error al restaurar auto-commit: " + e.getMessage());
			}
		}
	}

	public boolean grupoExiste(String nombreGrupo) throws ErrorInsertarDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT COUNT(*) FROM grupo WHERE nombre_grupo = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, nombreGrupo);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}
			else {
				return false;
			}

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al verificar existencia del grupo: " + e.getMessage());
		}
	}

	public boolean grupoExisteExcluyendoId(String nombreGrupo, int idGrupo)
			throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		String query = "SELECT COUNT(*) FROM grupo WHERE nombre_grupo = ? AND id != ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, nombreGrupo);
			ps.setInt(2, idGrupo);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}
			else {
				return false;
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al verificar existencia del grupo: " + e.getMessage());
		}
	}

	public void editarGrupo(EditarGrupoRequestDTO grupo)
			throws ErrorActualizarRegistro {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		String query = "UPDATE grupo SET nombre_grupo = ? WHERE id = ? AND estado = 1";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, grupo.getNuevoNombreGrupo());
			ps.setInt(2, grupo.getIdGrupo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected == 0) {
				throw new ErrorActualizarRegistro("No se pudo actualizar el grupo");
			}

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al actualizar el grupo: " + e.getMessage());
		}
	}

	public void cambiarEstadoGrupo(
			EditarEstadoGrupoRquestDTO editarEstadoGrupoRquestDTO)
			throws ErrorActualizarRegistro {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		String query = "UPDATE grupo SET estado = ? WHERE id = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, editarEstadoGrupoRquestDTO.isNuevoEstado() ? 1 : 0);
			ps.setInt(2, editarEstadoGrupoRquestDTO.getIdGrupo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected == 0) {
				throw new ErrorActualizarRegistro(
						"No se pudo cambiar el estado del grupo");
			}

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al cambiar el estado del grupo: " + e.getMessage());
		}
	}

}
