package com.api_videojuego.db.usuario.crear;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CrearUsuarioAdminDB {

  public void registrarUsuarioAdmin(String nombreCompleto, Integer idUsuario) throws Exception {

    Connection conn = DBConnectionSingleton.getInstance().getConnection();

    String query = "INSERT INTO administrador_sistema(id_usuario,nombre_completo) VALUES (?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setInt(1, idUsuario);
      ps.setString(2, nombreCompleto);

      ps.executeUpdate();

    } catch (SQLException e) {
      throw new ErrorInsertarDB("Error al registrar el administrador en la base de datos: " + e.getMessage());
    }

  }

}
