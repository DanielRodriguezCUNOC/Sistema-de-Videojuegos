package com.api_videojuego.resources.endpoints.cliente;

import com.api_videojuego.services.ClienteService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;

@WebServlet("/cliente/insertar")
public class InsertarCiente extends HttpServlet {

  @POST
  public String insertarCliente(String nombre, String email) {

    ClienteService clienteService = new ClienteService();
    return clienteService.insertarCliente(nombre, email);
  }

  @GET
  public String getInsertarCliente() {
    return "Endpoint para insertar un cliente";
  }

}
