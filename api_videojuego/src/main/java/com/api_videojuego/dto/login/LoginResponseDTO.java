package com.api_videojuego.dto.login;

public class LoginResponseDTO {

  private Integer idUsuario;
  private Integer idRol;

  public LoginResponseDTO() {
  }

  public LoginResponseDTO(Integer idUsuario, Integer idRol) {
    this.idUsuario = idUsuario;
    this.idRol = idRol;
  }

  // * Getters and Setters */
  public Integer getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(Integer idUsuario) {
    this.idUsuario = idUsuario;
  }

  public Integer getIdRol() {
    return idRol;
  }

  public void setIdRol(Integer idRol) {
    this.idRol = idRol;
  }

}
