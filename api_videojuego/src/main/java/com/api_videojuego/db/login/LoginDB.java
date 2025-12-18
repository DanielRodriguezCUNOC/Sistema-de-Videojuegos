package com.api_videojuego.db.login;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.login.LoginResponseDTO;
import com.api_videojuego.excepciones.CredencialesInvalidas;
import com.api_videojuego.excepciones.ErrorConsultaDB;

public class LoginDB {

  public boolean validarCredenciales(String correoUsuario, String passwordEncriptada) throws Exception {
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

  public LoginResponseDTO obtenerDatosUsuario(String correoUsuario) throws Exception {
    Connection conn = DBConnectionSingleton.getInstance().getConnection();

    String query = "SELECT correo_usuario, id_rol, avatar FROM usuario WHERE correo_usuario = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, correoUsuario);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          String correo = rs.getString("correo_usuario");
          Integer idRol = rs.getInt("id_rol");
          InputStream avatar = rs.getBinaryStream("avatar");

          return new LoginResponseDTO(correo, idRol, avatar);
        } else {
          throw new CredencialesInvalidas("Usuario no encontrado");
        }
      }
    } catch (SQLException e) {
      throw new ErrorConsultaDB("Error al obtener datos del usuario");
    }
  }

}
