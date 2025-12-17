package com.api_videojuego.dto.usuario;

import java.time.LocalDate;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class CrearUsuarioDTO {

  @FormDataParam("correo_usuario")
  private String correo_usuario;
  @FormDataParam("nickname")
  private String nickname;
  @FormDataParam("password")
  private String password;
  @FormDataParam("fecha_nacimiento")
  private LocalDate fecha_nacimiento;
  @FormDataParam("numero_telefonico")
  private String numero_telefonico;
  @FormDataParam("pais")
  private String pais;
  @FormDataParam("avatar")
  private FormDataBodyPart avatarPart;

  // * Getters and Setters */

  public String getCorreo_usuario() {
    return correo_usuario;
  }

  public void setCorreo_usuario(String correo_usuario) {
    this.correo_usuario = correo_usuario;
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

  public LocalDate getFecha_nacimiento() {
    return fecha_nacimiento;
  }

  public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
    this.fecha_nacimiento = fecha_nacimiento;
  }

  public String getNumero_telefonico() {
    return numero_telefonico;
  }

  public void setNumero_telefonico(String numero_telefonico) {
    this.numero_telefonico = numero_telefonico;
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

  public boolean usuarioValido() {
    return correo_usuario != null && !correo_usuario.isBlank() &&
        nickname != null && !nickname.isBlank() &&
        password != null && !password.isBlank() &&
        fecha_nacimiento != null &&
        numero_telefonico != null && !numero_telefonico.isBlank() &&
        pais != null && !pais.isBlank() &&
        avatarPart != null;
  }

  public long getAvatarSize() {
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

  public String getAvatarType() {
    try {
      return avatarPart.getMediaType().toString();
    } catch (Exception e) {
      return "";
    }
  }

}
