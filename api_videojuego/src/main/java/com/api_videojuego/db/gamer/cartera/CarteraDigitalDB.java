package com.api_videojuego.db.gamer.cartera;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.usuario.crear.CrearUsuarioGamerDTO;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CarteraDigitalDB {

	public void crearCarteraDigital(CrearUsuarioGamerDTO crearUsuarioDTO,
			Integer idUsuario) throws ErrorInsertarDB {

		Connection conn = DBConnectionSingleton.getInstance().getConnection();

		String sql = "INSERT INTO cartera_digital (id_usuario, saldo) VALUES (?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			ps.setBigDecimal(2, BigDecimal.valueOf(0.00));
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ErrorInsertarDB("Error al crar la cartera digital");
		}
	}
}
