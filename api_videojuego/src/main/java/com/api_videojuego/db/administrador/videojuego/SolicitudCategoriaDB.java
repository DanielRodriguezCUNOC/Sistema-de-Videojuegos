package com.api_videojuego.db.administrador.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.api_videojuego.db.administrador.categoria.CRUDCategoriaDB;
import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.db.empresa.videojuego.CategoriaVideojuegoDB;
import com.api_videojuego.db.empresa.videojuego.ClasificacionVideojuegoDB;
import com.api_videojuego.db.empresa.videojuego.VideojuegoDesarrolladoraDB;
import com.api_videojuego.dto.administrador.videojuego.ListaSolicitudVideojuegoDTO;
import com.api_videojuego.dto.administrador.videojuego.SolicitudVideojuegoRequestDTO;
import com.api_videojuego.dto.administrador.videojuego.SolicitudVideojuegoResponseDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEliminarRegistro;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class SolicitudCategoriaDB {

	public Integer generarSolicitudCategoria(Integer idVideojuego,
			String idUsuario, List<String> categorias, Connection conn)
			throws ErrorInsertarDB {

		String sql = "INSERT INTO solicitud_categoria (id_usuario, id_videojuego, categoria, estado) VALUES (?, ?, ?, 'pendiente')";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {

			int registrosInsertados = 0;

			for (String categoria : categorias) {
				stmt.setInt(1, Integer.parseInt(idUsuario));
				stmt.setInt(2, idVideojuego);
				stmt.setString(3, categoria);

				int resultado = stmt.executeUpdate();

				if (resultado > 0) {
					registrosInsertados++;
				}
			}

			return registrosInsertados;

		} catch (SQLException e) {
			throw new ErrorInsertarDB("Error al generar solicitudes de categoría: ");
		}
	}

	public void aceptarSolicitudCategoria(
			SolicitudVideojuegoRequestDTO solicitudDTO) throws ErrorInsertarDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		CRUDCategoriaDB crudCategoriaDB = new CRUDCategoriaDB();
		CategoriaVideojuegoDB categoriaVideojuegoDB = new CategoriaVideojuegoDB();
		VideojuegoDB videojuegoDB = new VideojuegoDB();

		try {
			// * Iniciar transaccion */
			conn.setAutoCommit(false);

			// * Obtener el id de la categoria mediante su nombre/
			Integer idCategoria = crudCategoriaDB
					.obtenerIdCategoriaPorNombre(solicitudDTO.getCategoria());

			// * Agregar el registro en la tabla categoria_videojuego */
			categoriaVideojuegoDB.agregarRegistro(idCategoria,
					solicitudDTO.getIdVideojuego());

			// * Cambiar el estado del videojuego a 'activo' */
			videojuegoDB.cambiarEstadoVideojuego(solicitudDTO.getIdVideojuego(), 1,
					conn);

			// * Eliminar el registro de la solicitud */
			eliminarRegistro(solicitudDTO.getIdSolicitud());

			// * Confirmar la transacción */
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
			throw new ErrorInsertarDB(
					"No se pudo aceptar la solicitud de categoría.");
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
		}

	}

	public void eliminarRegistro(Integer idSolicitud)
			throws ErrorEliminarRegistro {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "DELETE FROM solicitud_categoria WHERE id_solicitud = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idSolicitud);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ErrorEliminarRegistro(
					"No se pudo eliminar el registro de la base de datos.");
		}
	}

	public void rechazarSolicitudCategoria(
			SolicitudVideojuegoRequestDTO solicitudDTO) throws ErrorEliminarRegistro {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		ClasificacionVideojuegoDB clasificacionVideojuegoDB = new ClasificacionVideojuegoDB();
		VideojuegoDesarrolladoraDB videojuegoDesarrolladoraDB = new VideojuegoDesarrolladoraDB();
		ImagenVideojuegoDB imagenVideojuegoDB = new ImagenVideojuegoDB();
		VideojuegoDB videojuegoDB = new VideojuegoDB();

		try {
			// * Iniciar transacción */
			conn.setAutoCommit(false);

			// * Eliminar registros de videojuego_desarrolladora */
			videojuegoDesarrolladoraDB
					.eliminarRegistro(solicitudDTO.getIdVideojuego());

			// * Eliminar registros de clasificacion_videojuego */
			clasificacionVideojuegoDB
					.eliminarRegistro(solicitudDTO.getIdVideojuego());

			// * Eliminar imágenes del videojuego

			imagenVideojuegoDB
					.eliminarImagenesVideojuego(solicitudDTO.getIdVideojuego(), conn);

			// * Eliminar el videojuego */
			videojuegoDB.eliminarVideojuego(solicitudDTO.getIdVideojuego(), conn);

			// * Confirmar la transacción */
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
			throw new ErrorEliminarRegistro(
					"No se pudo rechazar la solicitud de categoría");
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
		}
	}

	public int solicitudesRestantesPorVideojuego(Integer idVideojuego)
			throws ErrorConsultaDB {
		String query = "SELECT COUNT(*) AS contador FROM solicitud_categoria WHERE id_videojuego = ?";

		try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idVideojuego);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("contador");
				}
				else {
					return 0;
				}
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al contar las solicitudes por videojuego.");
		}
	}

	public boolean tieneCategoriasAceptadas(Integer idVideojuego)
			throws ErrorConsultaDB {

		String query = "SELECT COUNT(*) AS contador FROM categoria_videojuego WHERE id_videojuego = ?";

		try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idVideojuego);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("contador") > 0;
				}
				return false;
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al verificar si el videojuego tiene categorías aceptadas.");
		}
	}

	public ListaSolicitudVideojuegoDTO listarSolicitudesVideojuego()
			throws ErrorConsultaDB {
		String query = "SELECT sc.id_solicitud, sc.id_videojuego, v.titulo AS titulo_videojuego, "
				+ "sc.categoria, sc.estado, u.id_usuario, u.nombre_usuario "
				+ "FROM solicitud_categoria sc "
				+ "JOIN videojuego v ON sc.id_videojuego = v.id_videojuego "
				+ "JOIN usuario u ON sc.id_usuario = u.id_usuario "
				+ "ORDER BY sc.id_solicitud";

		ListaSolicitudVideojuegoDTO listaSolicitudes = new ListaSolicitudVideojuegoDTO();

		try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				SolicitudVideojuegoResponseDTO solicitudDTO = new SolicitudVideojuegoResponseDTO();
				solicitudDTO.setIdSolicitud(rs.getInt("id_solicitud"));
				solicitudDTO.setIdVideojuego(rs.getInt("id_videojuego"));
				solicitudDTO.setNombreVideojuego(rs.getString("titulo_videojuego"));
				solicitudDTO.setNombreCategoria(rs.getString("categoria"));
				solicitudDTO.setEstadoSolicitud(rs.getString("estado"));

				listaSolicitudes.agregarSolicitud(rs.getInt("id_solicitud"),
						rs.getInt("id_videojuego"), rs.getString("titulo_videojuego"),
						rs.getString("categoria"), rs.getString("estado"));
			}

			return listaSolicitudes;

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al listar las solicitudes de videojuegos.");
		}

	}

}
