package com.api_videojuego.db.gamer.cartera;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.gamer.cartera.RecargarCarteraRequestDTO;
import com.api_videojuego.dto.usuario.crear.CrearUsuarioGamerDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CarteraDigitalDB {

	public void crearCarteraDigital(CrearUsuarioGamerDTO crearUsuarioDTO,
			Integer idUsuario) throws ErrorInsertarDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "INSERT INTO cartera_digital (id_usuario, saldo) VALUES (?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idUsuario);
			ps.setBigDecimal(2, BigDecimal.valueOf(0.00));
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorInsertarDB("Error al crar la cartera digital");
		}
	}

	public void agregarSaldo(RecargarCarteraRequestDTO requestDTO)
			throws ErrorActualizarRegistro {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE cartera_digital SET saldo = saldo + ? WHERE id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setBigDecimal(1, requestDTO.getMonto());
			ps.setInt(2, requestDTO.getIdUsuario());
			int filasActualizadas = ps.executeUpdate();

			if (filasActualizadas == 0) {
				throw new ErrorActualizarRegistro(
						"No se encontró la cartera digital del usuario");
			}

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al agregar saldo a la cartera digital");
		}
	}

	public BigDecimal obtenerSaldo(Integer idUsuario) throws ErrorConsultaDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "SELECT saldo FROM cartera_digital WHERE id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idUsuario);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getBigDecimal("saldo");
				}
				else {
					throw new ErrorConsultaDB(
							"No se encontró la cartera digital del usuario");
				}
			}

		} catch (SQLException e) {
			throw new ErrorConsultaDB(
					"Error al obtener el saldo de la cartera digital");
		}
	}

	public void descontarSaldo(Integer idUsuario, BigDecimal monto)
			throws ErrorActualizarRegistro {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "UPDATE cartera_digital SET saldo = saldo - ? WHERE id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setBigDecimal(1, monto);
			ps.setInt(2, idUsuario);
			int filasActualizadas = ps.executeUpdate();

			if (filasActualizadas == 0) {
				throw new ErrorActualizarRegistro(
						"No se encontró la cartera digital del usuario");
			}

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al descontar saldo de la cartera digital");
		}
	}

}
