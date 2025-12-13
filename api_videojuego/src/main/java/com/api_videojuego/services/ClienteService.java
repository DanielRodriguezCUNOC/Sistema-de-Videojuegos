package com.api_videojuego.services;

import com.api_videojuego.db.clientes.ClienteDB;

public class ClienteService {

  public String insertarCliente(String nombre, String email) {

    ClienteDB clienteDB = new ClienteDB();
    try {
      boolean exito = clienteDB.insertarCliente(nombre, email);
      if (!exito) {
        return "Error al insertar el cliente";
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "Error al insertar el cliente";
    }
    return "Cliente insertado con éxito";
  }
}
