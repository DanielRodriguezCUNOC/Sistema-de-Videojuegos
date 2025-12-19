package com.api_videojuego.db.usuario.crear;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorConsultaDB;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CrearUsuarioDB {

  public boolean existeUsuario(String correo_usuario) throws Exception {

    Connection conn = DBConnectionSingleton.getInstance().getConnection();

    String query = "SELECT COUNT(*) AS count FROM usuario WHERE correo_usuario = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setString(1, correo_usuario);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {

        int count = rs.getInt("count");

        return count > 0;

      }
    } catch (SQLException e) {
      throw new ErrorConsultaDB(
          "Error al verificar si el usuario ya está registrado: "
              + e.getMessage());
    }
    return false;
  }

  public void registrarUsuario(Integer idRol, String correoUsuario,
      String password, LocalDate fechaNacimiento, String numeroTelefonico,
      String pais, InputStream avatarStream) throws Exception {

    Connection conn = DBConnectionSingleton.getInstance().getConnection();

    String query = "INSERT INTO usuario (id_rol, correo_usuario, password, fecha_nacimiento, numero_telefonico, pais, avatar) VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setInt(1, idRol);
      ps.setString(2, correoUsuario);
      ps.setString(3, password);
      ps.setObject(4, fechaNacimiento);
      ps.setString(5, numeroTelefonico);
      ps.setString(6, pais);
      ps.setBlob(7, avatarStream);
      ps.executeUpdate();

    } catch (SQLException e) {
      throw new ErrorInsertarDB(
          "Error al registrar el usuario en la base de datos: "
              + e.getMessage());
    }

  }

  public Integer obtenerIdUsuarioPorCorreo(String correoUsuario)
      throws Exception {

    Connection conn = DBConnectionSingleton.getInstance().getConnection();

    String query = "SELECT id_usuario FROM usuario WHERE correo_usuario = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setString(1, correoUsuario);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        return rs.getInt("id_usuario");
      }
      else {
        throw new Exception(
            "Usuario no encontrado con el correo: " + correoUsuario);
      }

    } catch (SQLException e) {
      throw new ErrorConsultaDB(
          "Error al obtener el ID del usuario: " + e.getMessage());
    }
  }

}
