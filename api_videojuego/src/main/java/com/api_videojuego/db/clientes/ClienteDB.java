package com.api_videojuego.db.clientes;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.api_videojuego.db.connection.DBConnectionSingleton;

public class ClienteDB {

  public boolean insertarCliente(String nombre, String email) throws Exception {
    Connection conn = DBConnectionSingleton.getInstance().getConnection();
    String sql = "INSERT INTO clientes (nombre_cliente, ciudad) VALUES (?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, nombre);
      ps.setString(2, email);
      int filasInsertadas = ps.executeUpdate();
      return filasInsertadas > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
