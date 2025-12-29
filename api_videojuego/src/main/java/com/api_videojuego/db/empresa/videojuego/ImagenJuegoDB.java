package com.api_videojuego.db.empresa.videojuego;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.api_videojuego.db.administrador.banner.BannerDB;
import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.empresa.imagen.ImagenVideojuegoDTO;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class ImagenJuegoDB {

	public boolean guardarImagen(Integer idVideojuego, InputStream imagenPart)
			throws ErrorInsertarDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		try {
			conn.setAutoCommit(false);

			BannerDB bannerDB = new BannerDB();

			Integer idImagen = registrarImagenVideojuego(idVideojuego, imagenPart,
					conn);

			Integer idBanner = bannerDB.registrarBannerPorDefecto(idImagen, conn);

			if (idImagen > 0 && idBanner > 0) {
				conn.commit();
				return true;

			}
			else {
				conn.rollback();
				throw new ErrorInsertarDB(
						"Error al registrar la imagen del videojuego");
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				throw new ErrorInsertarDB("Error al guardar la imagen del videojuego.");
			}
			throw new ErrorInsertarDB("Error al guardar la imagen del videojuego.");
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
		}

	}

	public Integer registrarImagenVideojuego(Integer idVideojuego,
			InputStream imagenPart, Connection conn) throws ErrorInsertarDB {

		String query = "INSERT INTO imagen_juego (id_videojuego, imagen) VALUES (?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, idVideojuego);
			ps.setBlob(2, imagenPart);

			int filasAfectadas = ps.executeUpdate();

			if (filasAfectadas > 0) {
				try (var rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						return rs.getInt(1);
					}
				}
			}

			throw new ErrorInsertarDB(
					"No se pudo registrar la imagen del videojuego.");

		} catch (SQLException e) {
			throw new ErrorInsertarDB("Error al registrar la imagen del videojuego.");
		}

	}

	public List<ImagenVideojuegoDTO> obtenerTodasLasImagenes()
			throws ErrorConsultaDB {

		String query = "SELECT ij.id, ij.id_videojuego, ij.imagen, v.titulo "
				+ "FROM imagen_juego ij "
				+ "JOIN videojuego v ON ij.id_videojuego = v.id_videojuego "
				+ "ORDER BY ij.id";

		List<ImagenVideojuegoDTO> imagenes = new ArrayList<>();

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		try (PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				ImagenVideojuegoDTO imagen = new ImagenVideojuegoDTO();
				imagen.setId(rs.getInt("id"));
				imagen.setIdVideojuego(rs.getInt("id_videojuego"));
				imagen.setTituloVideojuego(rs.getString("titulo"));
				imagen.setImagen(rs.getBytes("imagen"));

				imagenes.add(imagen);
			}
			return imagenes;
		} catch (SQLException e) {
			throw new ErrorConsultaDB("Error al obtener las imágenes");
		}
	}

}
