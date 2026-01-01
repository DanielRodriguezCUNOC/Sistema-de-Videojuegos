package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.videojuego.ComentarioRequestDTO;
import com.api_videojuego.dto.videojuego.ComentarioResponseDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class ComentarioDB {

	public List<ComentarioResponseDTO> obtenerComentariosPorVideojuego(
			Integer idVideojuego) throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		List<ComentarioResponseDTO> comentarios = new ArrayList<>();

		String query = "SELECT " + "c.id as idComentario, "
				+ "c.comentario_id as idComentarioPadre, " + "ug.nickname, "
				+ "c.comentario, " + "c.fecha_comentario " + "FROM comentario c "
				+ "INNER JOIN usuario_gamer ug ON c.id_usuario = ug.id_usuario "
				+ "WHERE c.id_videojuego = ? " +
				// *Ordena por la fecha del comentario y de forma ascendente */
				"ORDER BY c.fecha_comentario ASC";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ComentarioResponseDTO comentario = new ComentarioResponseDTO();

				comentario.setIdComentario(rs.getInt("idComentario"));
				comentario
						.setIdComentarioPadre(rs.getObject("idComentarioPadre") != null
								? rs.getInt("idComentarioPadre")
								: null);
				comentario.setNickname(rs.getString("nickname"));
				comentario.setComentario(rs.getString("comentario"));
				comentario.setFechaComentario(rs.getDate("fecha_comentario"));

				comentarios.add(comentario);
			}

			return comentarios;

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener los comentarios del videojuego desde la base de datos: "
							+ e.getMessage());
		}

	}

	public void registrarComentarioVideojuego(
			ComentarioRequestDTO comentarioRequest) throws ErrorInsertarDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "INSERT INTO comentario ( id_usuario, id_videojuego, comentario, comentario_id, fecha_comentario) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, comentarioRequest.getIdUsuario());
			ps.setInt(2, comentarioRequest.getIdVideojuego());
			ps.setString(3, comentarioRequest.getComentario());

			if (comentarioRequest.getIdComentarioPadre() != null) {
				ps.setInt(4, comentarioRequest.getIdComentarioPadre());
			}
			else {
				ps.setNull(4, Types.INTEGER);
			}
			ps.setDate(5, Date.valueOf(LocalDate.now()));

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al registrar el comentario en la base de datos: "
							+ e.getMessage());
		}

	}
}
