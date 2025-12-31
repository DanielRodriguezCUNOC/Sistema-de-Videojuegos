package com.api_videojuego.db.tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.videojuego.DatosVideojuegoTiendaDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class TiendaDB {

	public List<DatosVideojuegoTiendaDTO> obtenerVideojuegosParaTienda(int offset,
			int limit) throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		List<DatosVideojuegoTiendaDTO> videojuegos = new ArrayList<>();

		String query = "SELECT v.id_videojuego, v.titulo, v.precio, "
				+ "(SELECT ij.imagen FROM imagen_juego ij WHERE ij.id_videojuego = v.id_videojuego LIMIT 1) as imagen "
				+ "FROM videojuego v " + "WHERE v.estado = 1 "
				+ "ORDER BY v.id_videojuego ASC " + "LIMIT ? OFFSET ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, limit);
			ps.setInt(2, offset);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DatosVideojuegoTiendaDTO videojuego = new DatosVideojuegoTiendaDTO(
						rs.getInt("id_videojuego"), rs.getString("titulo"),
						rs.getBigDecimal("precio"), rs.getBytes("imagen"));
				videojuegos.add(videojuego);

			}
			return videojuegos;

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al intentar obtener los videojuegos desde la BD");
		}

	}

	public DatosVideojuegoTiendaDTO obtenerVideojuegosPorTitulo(String titulo)
			throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		DatosVideojuegoTiendaDTO videojuego = null;

		String query = "SELECT v.id_videojuego, v.titulo, v.precio, "
				+ "(SELECT ij.imagen FROM imagen_juego ij WHERE ij.id_videojuego = v.id_videojuego LIMIT 1) as imagen "
				+ "FROM videojuego v " + "WHERE v.estado = 1 AND v.titulo = ? "
				+ "ORDER BY v.id_videojuego ASC ";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, titulo);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				videojuego = new DatosVideojuegoTiendaDTO(rs.getInt("id_videojuego"),
						rs.getString("titulo"), rs.getBigDecimal("precio"),
						rs.getBytes("imagen"));

			}
			return videojuego;

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al intentar obtener los videojuegos desde la BD");
		}

	}

	public List<DatosVideojuegoTiendaDTO> obtenerVideojuegosPorCategoria(
			int idCategoria) throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		List<DatosVideojuegoTiendaDTO> videojuegos = new ArrayList<>();

		String query = "SELECT v.id_videojuego, v.titulo, v.precio, "
				+ "(SELECT ij.imagen FROM imagen_juego ij WHERE ij.id_videojuego = v.id_videojuego LIMIT 1) as imagen "
				+ "FROM videojuego v "
				+ "JOIN categoria_videojuego vc ON v.id_videojuego = vc.id_videojuego "
				+ "WHERE v.estado = 1 AND vc.id_categoria = ? "
				+ "ORDER BY v.id_videojuego ASC ";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idCategoria);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DatosVideojuegoTiendaDTO videojuego = new DatosVideojuegoTiendaDTO(
						rs.getInt("id_videojuego"), rs.getString("titulo"),
						rs.getBigDecimal("precio"), rs.getBytes("imagen"));
				videojuegos.add(videojuego);

			}
			return videojuegos;

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al intentar obtener los videojuegos desde la BD");
		}

	}

}
