package com.api_videojuego.dto.usuario.crear;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class CrearUsuarioAdminDTO {
  @FormDataParam("correoUsuario")
  private String correoUsuario;
  @FormDataParam("nombreCompleto")
  private String nombreCompleto;
  @FormDataParam("password")
  private String password;
  @FormDataParam("fechaNacimiento")
  private String fechaNacimiento;
  @FormDataParam("numeroTelefonico")
  private String numeroTelefonico;
  @FormDataParam("pais")
  private String pais;
  @FormDataParam("avatar")
  private FormDataBodyPart avatarPart;

  // * Getters and Setters */

  public String getCorreoUsuario() {
    return correoUsuario;
  }

  public void setCorreoUsuario(String correoUsuario) {
    this.correoUsuario = correoUsuario;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(String fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getNumeroTelefonico() {
    return numeroTelefonico;
  }

  public void setNumeroTelefonico(String numeroTelefonico) {
    this.numeroTelefonico = numeroTelefonico;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  public FormDataBodyPart getAvatarPart() {
    return avatarPart;
  }

  public void setAvatarPart(FormDataBodyPart avatarPart) {
    this.avatarPart = avatarPart;
  }

  public boolean usuarioAdminValido() {
    return correoUsuario != null && !correoUsuario.isBlank() && password != null
        && !password.isBlank() && fechaNacimiento != null
        && numeroTelefonico != null && !numeroTelefonico.isBlank()
        && pais != null && !pais.isBlank() && avatarPart != null;
  }

  public long getAvatarAdminSize() {
    if (avatarPart != null)
      return avatarPart.getContentDisposition().getSize();

    return 0;

  }

  public String getAvatarAdminType() {
    if (avatarPart != null)
      return avatarPart.getMediaType().toString();

    return "";
  }
}
