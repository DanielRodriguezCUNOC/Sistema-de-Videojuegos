package com.api_videojuego.dto.login;

import java.io.InputStream;

public class LoginResponseDTO {

  private String idUsuario;
  private Integer idRol;
  private String correoUsuario;
  private InputStream avatar;

  public LoginResponseDTO() {
  }

  public LoginResponseDTO(String idUsuario, Integer idRol, String correoUsuario,
      InputStream avatar) {
    this.idUsuario = idUsuario;
    this.idRol = idRol;
    this.correoUsuario = correoUsuario;
    this.avatar = avatar;

  }

  // Getters and Setters
  public String getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

  public Integer getIdRol() {
    return idRol;
  }

  public void setIdRol(Integer idRol) {
    this.idRol = idRol;
  }

  public String getCorreoUsuario() {
    return correoUsuario;
  }

  public void setCorreoUsuario(String correoUsuario) {
    this.correoUsuario = correoUsuario;
  }

  public InputStream getAvatar() {
    return avatar;
  }

  public void setAvatar(InputStream avatar) {
    this.avatar = avatar;
  }

}
