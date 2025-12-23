package com.api_videojuego.db.usuario.crear;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.api_videojuego.db.connection.DBConnectionSingleton;
import com.api_videojuego.excepciones.ErrorInsertarDB;

public class CrearUsuarioEmpresaDB {

  public void registrarUsuarioEmpresa(String nombreCompleto, Integer idUsuario,
      Integer idEmpresa) throws ErrorInsertarDB {

    Connection conn = DBConnectionSingleton.getInstance().getConnection();

    String query = "INSERT INTO usuario_empresa(id_usuario,id_empresa,nombre_completo) VALUES (?, ?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setInt(1, idUsuario);
      ps.setInt(2, idEmpresa);
      ps.setString(3, nombreCompleto);

      ps.executeUpdate();

    } catch (SQLException e) {
      throw new ErrorInsertarDB(
          "Error al registrar el administrador en la base de datos: "
              + e.getMessage());
    }

  }
}
