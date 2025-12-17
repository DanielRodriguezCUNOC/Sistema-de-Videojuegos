package com.api_videojuego.excepciones;

public class UsuarioYaRegistrado extends Exception {
  public UsuarioYaRegistrado(String mensaje) {
    super(mensaje);
  }

}
