package com.api_videojuego.db.usuario;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.dto.usuario.CrearUsuarioDTO;
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
      throw new ErrorInsertarDB("Error al verificar si el usuario ya está registrado: " + e.getMessage());
    }
    return false;
  }

  public void registrarUsuario(CrearUsuarioDTO crearUsuarioDTO) throws Exception {

    Connection conn = DBConnectionSingleton.getInstance().getConnection();

    String query = "INSERT INTO usuario (correo_usuario, nickname, password, fecha_nacimiento, numero_telefonico, pais, avatar) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setString(1, crearUsuarioDTO.getCorreo_usuario());
      ps.setString(2, crearUsuarioDTO.getNickname());
      ps.setString(3, crearUsuarioDTO.getPassword());
      ps.setObject(4, crearUsuarioDTO.getFecha_nacimiento());
      ps.setString(5, crearUsuarioDTO.getNumero_telefonico());
      ps.setString(6, crearUsuarioDTO.getPais());
      try (InputStream avatarStream = crearUsuarioDTO.getAvatarPart().getValueAs(InputStream.class)) {
        ps.setBlob(7, avatarStream);
        ps.executeUpdate();
      }

    } catch (SQLException e) {
      throw new ErrorInsertarDB("Error al registrar el usuario en la base de datos: " + e.getMessage());
    }

  }

}
