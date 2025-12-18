package com.api_videojuego.dto.usuario.crear;

import java.time.LocalDate;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class CrearUsuarioGamerDTO {

  @FormDataParam("correoUsuario")
  private String correoUsuario;
  @FormDataParam("nickname")
  private String nickname;
  @FormDataParam("password")
  private String password;
  @FormDataParam("fechaNacimiento")
  private LocalDate fechaNacimiento;
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

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
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

  public boolean usuarioGamerValido() {
    return correoUsuario != null && !correoUsuario.isBlank() &&
        password != null && !password.isBlank() &&
        fechaNacimiento != null &&
        numeroTelefonico != null && !numeroTelefonico.isBlank() &&
        pais != null && !pais.isBlank() &&
        avatarPart != null;
  }

  public long getAvatarGamerSize() {
    try {
      long size = avatarPart.getContentDisposition().getSize();
      if (size == -1) {
        return size = avatarPart.getValueAs(byte[].class).length;
      } else {
        return size;
      }
    } catch (Exception e) {
      return 0;
    }
  }

  public String getAvatarGamerType() {
    try {
      return avatarPart.getMediaType().toString();
    } catch (Exception e) {
      return "";
    }
  }

}
