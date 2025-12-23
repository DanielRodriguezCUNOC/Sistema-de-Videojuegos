package com.api_videojuego.dto.login;

public class LoginRequestDTO {

  private String correoUsuario;

  private String password;

  public LoginRequestDTO() {
  }

  // * Getters and Setters */
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
    return correoUsuario != null && !correoUsuario.isBlank() && password != null
        && !password.isBlank();
  }

}
