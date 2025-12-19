package com.api_videojuego.db.administrador.categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.categoria.CrearCategoriaDTO;
import com.api_videojuego.dto.categoria.EditarCategoriaDTO;
import com.api_videojuego.dto.categoria.ListaCategoriaDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CRUDCategoriaDB {

	public void crearCategoria(CrearCategoriaDTO crearCategoriaDTO)
			throws Exception {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "INSERT INTO categoria (categoria) VALUES (?)";

		try (PreparedStatement ps = conn.prepareStatement(query);) {
			ps.setString(1, crearCategoriaDTO.getCategoria());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al insertar la categoria en la base de datos");
		}
	}

	public ListaCategoriaDTO obtenerCategorias() throws Exception {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT id_categoria, categoria FROM categoria";
		ListaCategoriaDTO listaCategoriaDTO = new ListaCategoriaDTO();
		try (PreparedStatement ps = conn.prepareStatement(query);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Integer idCategoria = rs.getInt("id_categoria");
				String categoria = rs.getString("categoria");
				listaCategoriaDTO.agregarCategoria(idCategoria, categoria);
			}
			return listaCategoriaDTO;

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener las categorias de la base de datos");
		}
	}

	public void editarCategoria(EditarCategoriaDTO editarCategoriaDTO)
			throws Exception {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE categoria SET categoria = ? WHERE id_categoria = ?";

		try (PreparedStatement ps = conn.prepareStatement(query);) {
			ps.setString(1, editarCategoriaDTO.getCategoria());
			ps.setInt(2, editarCategoriaDTO.getIdCategoria());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al editar la categoria en la base de datos");
		}

	}

	public void eliminarCategoria(Integer idCategoria) throws Exception {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "DELETE FROM categoria WHERE id_categoria = ?";

		try (PreparedStatement ps = conn.prepareStatement(query);) {
			ps.setInt(1, idCategoria);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al eliminar la categoria en la base de datos");
		}
	}

	public boolean categoriaExiste(String categoria) throws Exception {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT COUNT(*) FROM categoria WHERE categoria = ?";

		try (PreparedStatement ps = conn.prepareStatement(query);) {
			ps.setString(1, categoria);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al consultar la categoria en la base de datos");
		}

		return false;
	}

}