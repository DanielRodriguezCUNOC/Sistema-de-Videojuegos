package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class BannerDB {

	public Integer registrarBannerPorDefecto(Integer idImagenJuego,
			Connection conn) throws ErrorInsertarDB {

		String query = "INSERT INTO banner (id_imagen_juego, estado) VALUES (?, ?)";

		try (var ps = conn.prepareStatement(query)) {

			ps.setInt(1, idImagenJuego);
			ps.setBoolean(2, true);

			int filasInsertadas = ps.executeUpdate();

			if (filasInsertadas > 0) {
				return filasInsertadas;
			}
			else {
				throw new ErrorInsertarDB(
						"No se pudo registrar el banner por defecto.");
			}

		} catch (Exception e) {
			throw new ErrorInsertarDB(
					"Error al registrar el banner por defecto: " + e.getMessage());
		}

	}

	public List<Integer> obtenerBannersActivos() throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT id_imagen_juego FROM banner WHERE estado = true";

		List<Integer> banners = new ArrayList<>();

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				banners.add(rs.getInt("id_imagen_juego"));
			}

			return banners;

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener los banners desde el servidor");
		}

	}

	public boolean cambiarEstadoBanner(Integer idBanner, Integer estado)
			throws ErrorActualizarRegistro {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE banner SET estado = ? WHERE id_banner = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setBoolean(1, estado == 1);
			ps.setInt(2, idBanner);

			int filasAfectadas = ps.executeUpdate();

			return filasAfectadas > 0;

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro("Error al desactivar el banner.");
		}

	}

	public List<Integer> obtenerBanners() throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT id_imagen_juego FROM banner";

		List<Integer> banners = new ArrayList<>();
		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				banners.add(rs.getInt("id_imagen_juego"));
			}

			return banners;

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener los banners inactivos desde el servidor");
		}
	}

}
