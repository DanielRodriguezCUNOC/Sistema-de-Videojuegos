package com.api_videojuego.db.administrador.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.api_videojuego.db.administrador.categoria.CRUDCategoriaDB;
import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.db.empresa.videojuego.CategoriaVideojuegoDB;
import com.api_videojuego.dto.videojuego.PerfilVideojuegoResponseDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorEliminarRegistro;

public class VideojuegoDB {

	public void cambiarEstadoVideojuego(Integer idVideojuego, boolean estado,
			Connection conn) throws ErrorActualizarRegistro {

		String query = "UPDATE videojuego SET estado = ? WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setBoolean(1, estado);
			ps.setInt(2, idVideojuego);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al cambiar el estado del videojuego desde la tabla videojuego."
							+ e.getMessage());
		}

	}

	public void eliminarVideojuego(Integer idVideojuego, Connection conn)
			throws ErrorEliminarRegistro {
		String query = "DELETE FROM videojuego WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idVideojuego);
			ps.executeUpdate();
		} catch (

		SQLException e) {
			throw new ErrorEliminarRegistro(
					"Error al eliminar el videojuego de la tabla videojuego."
							+ e.getMessage());
		}
	}

	public PerfilVideojuegoResponseDTO obtenerPerfilVideojuego(
			Integer idVideojuego) throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		PerfilVideojuegoResponseDTO perfil = new PerfilVideojuegoResponseDTO();
		CategoriaVideojuegoDB categoriaVideojuegoDB = new CategoriaVideojuegoDB();
		CRUDCategoriaDB crudCategoriaDB = new CRUDCategoriaDB();
		String query = "SELECT titulo, descripcion, recursos_minimos, precio FROM videojuego WHERE id_videojuego = ?";
		try {
			conn.setAutoCommit(false);
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setInt(1, idVideojuego);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {

					// *Obtener categorias que pertenecen al videojuego */
					List<Integer> idsCategorias = categoriaVideojuegoDB
							.obtenerCategoriasPorVideojuego(idVideojuego, conn);

					// *Obtener los nombres de las categorias */
					for (Integer idCategoria : idsCategorias) {
						perfil.addCategoria(
								crudCategoriaDB.obtenerNombrePorId(idCategoria, conn));
					}

					perfil.setTitulo(rs.getString("titulo"));
					perfil.setDescripcion(rs.getString("descripcion"));
					perfil.setRecursosMinimos(rs.getString("recursos_minimos"));
					perfil.setPrecio(rs.getBigDecimal("precio"));
					conn.commit();
					return perfil;
				}
				else {
					throw new ErrorConsultaDB(
							"Ha ocurrido un error al momento de obtener los datos del videojuego");
				}

			} catch (SQLException e) {
				throw new ErrorConsultaDB(
						"Ha ocurrido un error al momento de obtener los datos del videojuego");
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				throw new ErrorConsultaDB(
						"Error al realizar rollback de la transacción: " + ex.getMessage());
			}
			throw new ErrorConsultaDB(
					"Ha ocurrido un error al momento de obtener los datos del videojuego");
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new ErrorConsultaDB(
						"Error al restablecer el modo de autocommit: " + e.getMessage());
			}
		}
	}
}