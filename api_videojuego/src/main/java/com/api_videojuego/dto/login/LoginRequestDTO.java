package com.api_videojuego.dto.login;

import jakarta.ws.rs.FormParam;

public class LoginRequestDTO {

  @FormParam("correoUsuario")
  private String correoUsuario;

  @FormParam("password")
  private String password;

  // Getters and Setters
  public String getCorreoUsuario() {
    return correoUsuario;
  }

  public void setCorreoUsuario(String correoUsuario) {
    this.correoUsuario = correoUsuario;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean loginValido() {
    return correoUsuario != null && !correoUsuario.isBlank() &&
        password != null && !password.isBlank();
  }

}
