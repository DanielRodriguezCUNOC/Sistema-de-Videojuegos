package com.api_videojuego.dto.login;

import java.io.InputStream;

public class LoginResponseDTO {

  private String correoUsuario;
  private Integer idRol;
  private InputStream avatar;

  public LoginResponseDTO() {
  }

  public LoginResponseDTO(String correoUsuario, Integer idRol, InputStream avatar) {
    this.correoUsuario = correoUsuario;
    this.idRol = idRol;
    this.avatar = avatar;
  }

  // Getters and Setters
  public String getCorreoUsuario() {
    return correoUsuario;
  }

  public void setCorreoUsuario(String correoUsuario) {
    this.correoUsuario = correoUsuario;
  }

  public Integer getIdRol() {
    return idRol;
  }

  public void setIdRol(Integer idRol) {
    this.idRol = idRol;
  }

  public InputStream getAvatar() {
    return avatar;
  }

  public void setAvatar(InputStream avatar) {
    this.avatar = avatar;
  }

}
