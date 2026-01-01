package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.empresa.comentarios.EditarEstadoVideojuegoRequestDTO;
import com.api_videojuego.dto.empresa.comentarios.EditarEstadoVideojuegoResponseDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEliminarRegistro;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class VideojuegoDesarrolladoraDB {

	public Integer registrarVideojuegoDesarrolladora(Integer idEmpresa,
			Integer idVideojuego, Connection conn) throws ErrorInsertarDB {

		String query = "INSERT INTO videojuego_desarrolladora (id_empresa, id_videojuego) VALUES (?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idEmpresa);
			ps.setInt(2, idVideojuego);

			int filasAfectadas = ps.executeUpdate();

			if (filasAfectadas > 0) {
				return filasAfectadas;
			}
			else {
				throw new ErrorInsertarDB(
						"No se pudo registrar la relacion videojuego-desarrolladora");
			}

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al registrar la relacion videojuego-desarrolladora: "
							+ e.getMessage());
		}
	}

	public void eliminarRegistro(Integer idVideojuego, Connection conn)
			throws ErrorEliminarRegistro {

		String query = "DELETE FROM videojuego_desarrolladora WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorEliminarRegistro(
					"Error al eliminar la relación videojuego-desarrolladora: "
							+ e.getMessage());
		}
	}

	public Integer obtenerIdEmpresaPorIdVidejuego(Integer idVideojuego,
			Connection conn) throws ErrorConsultaDB {

		String query = "SELECT id_empresa FROM videojuego_desarrolladora WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);

			var rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("id_empresa");
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"No se pudo obtener el id de la empresa desde la tabla videojuego_desarrolladora");
		}
	}

	public Integer idEmpresaPorIdVidejuego(Integer idVideojuego)
			throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT id_empresa FROM videojuego_desarrolladora WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);

			var rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("id_empresa");
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"No se pudo obtener el id de la empresa desde la tabla videojuego_desarrolladora"
							+ e.getMessage());

		}
	}

	public List<EditarEstadoVideojuegoResponseDTO> obtenerEstadoComentariosVideojuegosEmpresa(
			Integer idEmpresa) throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT vd.id_videojuego, v.titulo, vd.estado_comentario "
				+ "FROM videojuego_desarrolladora vd "
				+ "INNER JOIN videojuego v ON vd.id_videojuego = v.id_videojuego "
				+ "WHERE vd.id_empresa = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idEmpresa);
			ResultSet rs = ps.executeQuery();

			List<EditarEstadoVideojuegoResponseDTO> estados = new ArrayList<>();

			while (rs.next()) {
				EditarEstadoVideojuegoResponseDTO estado = new EditarEstadoVideojuegoResponseDTO(
						rs.getInt("id_videojuego"), rs.getString("titulo"),
						rs.getBoolean("estado_comentario"));
				estados.add(estado);
			}

			return estados;

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al consultar el estado de los comentarios de los videojuegos de la empresa: "
							+ e.getMessage());
		}
	}

	public void cambiarEstadoComentariosVideojuego(
			EditarEstadoVideojuegoRequestDTO editar) throws ErrorActualizarRegistro {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE videojuego_desarrolladora SET estado_comentario = ? WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setBoolean(1, editar.getEstadoComentarioVideojuego());
			ps.setInt(2, editar.getIdVideojuego());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al cambiar el estado de comentarios del videojuego desde la DB: "
							+ e.getMessage());
		}
	}

}
