package com.api_videojuego.db.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.login.LoginResponseDTO;
import com.api_videojuego.excepciones.CredencialesInvalidas;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class LoginDB {

  public boolean validarCredenciales(String correoUsuario,
      String passwordEncriptada) throws ErrorConsultaDB {
    Connection conn = DBConnectionSingleton.getInstance().getConnection();

    String query = "SELECT password FROM usuario WHERE correo_usuario = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, correoUsuario);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          String passwordBD = rs.getString("password");
          return passwordEncriptada.equals(passwordBD);
        }
        return false;
      }
    } catch (SQLException e) {
      throw new ErrorConsultaDB("Error al validar credenciales");
    }
  }

  public LoginResponseDTO obtenerDatosUsuario(String correoUsuario)
      throws CredencialesInvalidas, ErrorConsultaDB {
    Connection conn = DBConnectionSingleton.getInstance().getConnection();

    String query = "SELECT id_usuario, id_rol FROM usuario WHERE correo_usuario = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, correoUsuario);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          Integer idUsuario = rs.getInt("id_usuario");
          Integer idRol = rs.getInt("id_rol");

          return new LoginResponseDTO(idUsuario, idRol);
        }
        else {
          throw new CredencialesInvalidas("Usuario no encontrado");
        }
      }
    } catch (SQLException e) {
      throw new ErrorConsultaDB("Error al obtener datos del usuario");
    }
  }

}
