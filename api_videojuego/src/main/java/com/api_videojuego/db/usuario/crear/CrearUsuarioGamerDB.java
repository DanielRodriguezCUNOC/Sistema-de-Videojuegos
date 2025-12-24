package com.api_videojuego.db.usuario.crear;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CrearUsuarioGamerDB {

  public void registrarUsuarioGamer(Integer idUsuario, String nickname)
      throws ErrorInsertarDB {
    Connection conn = DBConnectionSingleton.getInstance().getConnection();

    String query = "INSERT INTO usuario_gamer(id_usuario, nickname) VALUES (?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setInt(1, idUsuario);
      ps.setString(2, nickname);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new ErrorInsertarDB("Error al registrar en la base de datos");
    }
  }
}
