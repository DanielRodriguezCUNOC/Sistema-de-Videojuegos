package com.api_videojuego.dto.login;

public class LoginResponseDTO {

  private String idUsuario;
  private Integer idRol;

  public LoginResponseDTO() {
  }

  public LoginResponseDTO(String idUsuario, Integer idRol) {
    this.idUsuario = idUsuario;
    this.idRol = idRol;
  }

  // * Getters and Setters */
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

}
