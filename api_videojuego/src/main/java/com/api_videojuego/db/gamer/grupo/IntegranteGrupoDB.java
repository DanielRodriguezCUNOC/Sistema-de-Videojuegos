package com.api_videojuego.db.gamer.grupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.gamer.grupo.EliminarIntegranteDTO;
import com.api_videojuego.dto.gamer.grupo.GrupoResponseDTO;
import com.api_videojuego.dto.gamer.grupo.IntegranteGrupoDTO;
import com.api_videojuego.dto.gamer.grupo.NuevoIntegranteRequestDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEliminarRegistro;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class IntegranteGrupoDB {

	public void verificarIntegranteEliminable(
			EliminarIntegranteDTO eliminarIntegranteDTO)
			throws ErrorEliminarRegistro {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		String query = "SELECT tipo_integrante FROM integrante_grupo WHERE id = ? AND estado = 1 AND id_grupo = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, eliminarIntegranteDTO.getIdIntegrante());
			ps.setInt(2, eliminarIntegranteDTO.getIdGrupo());
			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				throw new ErrorEliminarRegistro(
						"El integrante no existe o ya está inactivo");
			}

			String tipoIntegrante = rs.getString("tipo_integrante");
			if ("propietario".equals(tipoIntegrante)) {
				throw new ErrorEliminarRegistro(
						"No se puede eliminar al propietario del grupo");
			}

		} catch (SQLException e) {
			throw new ErrorEliminarRegistro(
					"Error al verificar integrante: " + e.getMessage());
		}
	}

	public void eliminarIntegrante(EliminarIntegranteDTO eliminarIntegranteDTO)
			throws ErrorEliminarRegistro {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		String query = "UPDATE integrante_grupo SET estado = 0 WHERE id = ? AND id_grupo = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, eliminarIntegranteDTO.getIdIntegrante());
			ps.setInt(2, eliminarIntegranteDTO.getIdGrupo());
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected == 0) {
				throw new ErrorEliminarRegistro(
						"No se pudo eliminar el integrante del grupo");
			}

		} catch (SQLException e) {
			throw new ErrorEliminarRegistro(
					"Error al eliminar integrante del grupo: " + e.getMessage());
		}
	}

	public List<GrupoResponseDTO> obtenerGruposUsuario(int idUsuario)
			throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		String sql = "SELECT g.id, g.nombre_grupo, g.estado, ig.id as id_integrante, ig.tipo_integrante, ug.nickname "
				+ "FROM grupo g "
				+ "JOIN integrante_grupo ig ON g.id = ig.id_grupo AND ig.estado = 1 "
				+ "JOIN usuario u ON ig.id_integrante = u.id_usuario "
				+ "JOIN usuario_gamer ug ON u.id_usuario = ug.id_usuario "
				+ "WHERE ig.id_integrante = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			ResultSet rs = ps.executeQuery();

			Map<Integer, GrupoResponseDTO> gruposMap = new HashMap<>();

			while (rs.next()) {
				int grupoId = rs.getInt("id");
				GrupoResponseDTO grupo = gruposMap.get(grupoId);

				if (grupo == null) {
					grupo = new GrupoResponseDTO();
					grupo.setIdGrupo(grupoId);
					grupo.setNombreGrupo(rs.getString("nombre_grupo"));
					grupo.setEstado(rs.getInt("estado") == 1);
					gruposMap.put(grupoId, grupo);
				}

				IntegranteGrupoDTO integrante = new IntegranteGrupoDTO();
				integrante.setIdIntegrante(rs.getInt("id_integrante"));
				integrante.setTipoIntegrante(rs.getString("tipo_integrante"));
				integrante.setNickname(rs.getString("nickname"));
				grupo.addIntegrante(integrante);
			}

			return new ArrayList<>(gruposMap.values());

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener grupos del usuario: " + e.getMessage());
		}
	}

	public void agregarIntegrante(
			NuevoIntegranteRequestDTO nuevoIntegranteRequestDTO)
			throws ErrorInsertarDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "INSERT INTO integrante_grupo (id_grupo, id_integrante, tipo_integrante, estado) VALUES (?, (SELECT id_usuario FROM usuario_gamer WHERE nickname = ?), 'miembro', 1)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, nuevoIntegranteRequestDTO.getIdGrupo());
			ps.setString(2, nuevoIntegranteRequestDTO.getNickname());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al intentar agregar el integrante al grupo: "
							+ e.getMessage());
		}
	}

}
