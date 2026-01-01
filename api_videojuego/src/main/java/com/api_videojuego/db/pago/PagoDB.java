package com.api_videojuego.db.pago;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.gamer.compra.ComprarVideojuegoRequestDTO;
import com.api_videojuego.excepciones.ErrorActualizarRegistro;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class PagoDB {

	public Integer registrarPagoVideojuego(ComprarVideojuegoRequestDTO requestDTO,
			BigDecimal comisionGlobal)
			throws ErrorInsertarDB, ErrorActualizarRegistro {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		try {
			conn.setAutoCommit(false);
			String query = "INSERT INTO pago (id_usuario, id_videojuego, monto, fecha_pago, comision_global) VALUES (?, ?, ?, ?, ?)";

			try (PreparedStatement ps = conn.prepareStatement(query,
					PreparedStatement.RETURN_GENERATED_KEYS)) {
				ps.setInt(1, requestDTO.getIdUsuario());
				ps.setInt(2, requestDTO.getIdVideojuego());
				ps.setBigDecimal(3, requestDTO.getPrecio());
				ps.setObject(4, LocalDate.now());
				ps.setBigDecimal(5, comisionGlobal);

				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					// * Descontar fondos del usuario
					descontarFondosUsuario(requestDTO.getIdUsuario(),
							requestDTO.getPrecio(), conn);
					conn.commit();
					return rs.getInt(1);
				}
				else {
					throw new ErrorInsertarDB(
							"No se pudo realizar el descuento de fondos del usuario"
									+ requestDTO.getIdUsuario());
				}

			} catch (SQLException e) {
				throw new ErrorInsertarDB(
						"No se pudo realizar la compra del videojuego" + e.getMessage());
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				throw new ErrorInsertarDB(
						"Error al realizar rollback de la transacción: " + ex.getMessage());
			}
			throw new ErrorInsertarDB(
					"Error al registrar el pago del videojuego: " + e.getMessage());
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new ErrorInsertarDB(
						"Error al restablecer el modo de autocommit: " + e.getMessage());
			}
		}

	}

	public void registrarPagoComisionEmpresa(Integer idEmpresa,
			BigDecimal comisionEspecifica, Integer idPago) throws ErrorInsertarDB {
		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String query = "INSERT INTO pago_comision_empresa (id_empresa, id_pago, comision_especifica) VALUES (?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idEmpresa);
			ps.setInt(2, idPago);
			ps.setBigDecimal(3, comisionEspecifica);

			int filasAfectadas = ps.executeUpdate();

			if (filasAfectadas == 0) {
				throw new ErrorInsertarDB(
						"No se pudo registrar el pago de comisión de la empresa"
								+ idEmpresa);
			}

		} catch (SQLException e) {
			throw new ErrorInsertarDB(
					"Error al registrar el pago de comisión de la empresa"
							+ e.getMessage());
		}
	}

	private void descontarFondosUsuario(Integer idUsuario, BigDecimal monto,
			Connection conn) throws ErrorActualizarRegistro {
		String query = "UPDATE cartera_digital SET saldo = saldo - ? WHERE id_usuario = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setBigDecimal(1, monto);
			ps.setInt(2, idUsuario);

			int filasAfectadas = ps.executeUpdate();

			if (filasAfectadas == 0) {
				throw new ErrorActualizarRegistro(
						"No se pudo descontar los fondos del usuario");
			}

		} catch (SQLException e) {
			throw new ErrorActualizarRegistro(
					"Error al descontar los fondos del usuario: " + e.getMessage());
		}
	}

}
