package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.gamer.biblioteca.VideojuegoCompradoRequestDTO;
import com.api_videojuego.dto.gamer.biblioteca.VideojuegoCompradoResponseDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class VideojuegoCompradoDB {

	public void registrarVideojuegoComprado(Integer idUsuario,
			Integer idVideojuego) throws ErrorInsertarDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "INSERT INTO videojuego_comprado (id_usuario, id_videojuego, instalado, fecha_compra) "
				+ "VALUES (?, ?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idUsuario);
			ps.setInt(2, idVideojuego);
			ps.setBoolean(3, false); // * No instalado por defecto */
			ps.setDate(4, Date.valueOf(LocalDate.now()));
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al registrar el videojuego comprado: " + e.getMessage());
		}
	}

	public List<VideojuegoCompradoResponseDTO> obtenerVideojuegosCompradosPorGamer(
			Integer idUsuario) throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		List<VideojuegoCompradoResponseDTO> videojuegosComprados = new ArrayList<>();

		String query = "SELECT v.id_videojuego, v.titulo, vc.instalado FROM videojuego v "
				+ " INNER JOIN videojuego_comprado vc ON v.id_videojuego = vc.id_videojuego "
				+ " WHERE vc.id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				VideojuegoCompradoResponseDTO videojuegoComprado = new VideojuegoCompradoResponseDTO(
						rs.getInt("id_videojuego"), rs.getString("titulo"),
						rs.getBoolean("instalado"));

				videojuegosComprados.add(videojuegoComprado);

			}
			return videojuegosComprados;
		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener los videojuegos comprados por el gamer");
		}
	}

	public void actualizaEstadoInstalacionVideojuego(
			VideojuegoCompradoRequestDTO requestDTO) throws ErrorActualizarRegistro {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE videojuego_comprado SET instalado = ? "
				+ " WHERE id_usuario = ? AND id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setBoolean(1, requestDTO.isEstadoInstalacion());
			ps.setInt(2, requestDTO.getIdUsuario());
			ps.setInt(3, requestDTO.getIdVideojuego());

			ps.executeUpdate();

		} catch (SQLException e) {

			throw new ErrorActualizarRegistro(
					"No se pudo realizar la instalacion/desinstalacion del videojuego");
		}

	}
}
