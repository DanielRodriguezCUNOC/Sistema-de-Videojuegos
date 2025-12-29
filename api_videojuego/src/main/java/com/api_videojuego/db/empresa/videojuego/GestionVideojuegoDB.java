package com.api_videojuego.db.empresa.videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.empresa.videojuego.EditarEstadoVideojuegoDTO;
import com.api_videojuego.dto.empresa.videojuego.EditarVideojuegoRequestDTO;
import com.api_videojuego.dto.empresa.videojuego.EditarVideojuegoResponseDTO;
import com.api_videojuego.dto.empresa.videojuego.ListaVideojuegosDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class GestionVideojuegoDB {

	public void cambiarEstadoVideojuego(
			EditarEstadoVideojuegoDTO editarEstadoVideojuegoDTO)
			throws ErrorActualizarRegistro {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE videojuego SET estado = ? WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setBoolean(1, editarEstadoVideojuegoDTO.getNuevoEstado());
			ps.setInt(2, editarEstadoVideojuegoDTO.getIdVideojuego());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al cambiar el estado del videojuego");
		}

	}

	public void cambiarPortadaVideojuego(Integer idVideojuego,
			byte[] nuevaPortada) throws ErrorActualizarRegistro {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE imagen_juego SET imagen = ? WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setBytes(1, nuevaPortada);
			ps.setInt(2, idVideojuego);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al cambiar la portada del videojuego");
		}

	}

	public void editarVideojuego(EditarVideojuegoRequestDTO editarVideojuegoDTO)
			throws ErrorActualizarRegistro {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE videojuego SET titulo = ?, descripcion = ?, precio = ?, recursos_minimos = ? WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, editarVideojuegoDTO.getTitulo());
			ps.setString(2, editarVideojuegoDTO.getDescripcion());
			ps.setDouble(3, editarVideojuegoDTO.getPrecio());
			ps.setString(4, editarVideojuegoDTO.getRecursosMinimos());
			ps.setInt(5, editarVideojuegoDTO.getIdVideojuego());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro("Error al editar el videojuego");
		}

	}

	public ListaVideojuegosDTO obtenerListaVideojuegos() throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT id_videojuego, titulo, estado FROM videojuego";

		ListaVideojuegosDTO listaVideojuegosDTO = new ListaVideojuegosDTO();

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				Integer idVideojuego = rs.getInt("id_videojuego");
				String titulo = rs.getString("titulo");
				Boolean estado = rs.getBoolean("estado");

				listaVideojuegosDTO.agregarVideojuego(idVideojuego, titulo, estado);
			}
			return listaVideojuegosDTO;

		} catch (SQLException e) {
			throw new ErrorConsultaDB("Error al obtener la lista de videojuegos");
		}
	}

	public EditarVideojuegoResponseDTO obtenerDatosVideojuegoPorId(
			Integer idVideojuego) throws ErrorConsultaDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();
		EditarVideojuegoResponseDTO editarVideojuegoResponseDTO = null;

		String query = "SELECT titulo, descripcion, precio, recursos_minimos FROM videojuego WHERE id_videojuego = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idVideojuego);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				editarVideojuegoResponseDTO = new EditarVideojuegoResponseDTO(
						rs.getString("titulo"), rs.getString("descripcion"),
						rs.getBigDecimal("precio"), rs.getString("recursos_minimos"));

			}
			return editarVideojuegoResponseDTO;

		} catch (SQLException e) {
			throw new ErrorConsultaDB("No se pudo obtener los datos del videojuego");
		}

	}

}
