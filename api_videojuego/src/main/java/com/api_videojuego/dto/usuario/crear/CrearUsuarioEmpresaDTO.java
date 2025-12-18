package com.api_videojuego.dto.usuario.crear;

import java.time.LocalDate;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class CrearUsuarioEmpresaDTO {
  @FormDataParam("correoUsuario")
  private String correoUsuario;
  @FormDataParam("nombreCompleto")
  private String nombreCompleto;
  @FormDataParam("idEmpresa")
  private Integer idEmpresa;
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

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public Integer getIdEmpresa() {
    return idEmpresa;
  }

  public void setIdEmpresa(Integer idEmpresa) {
    this.idEmpresa = idEmpresa;
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

  public boolean usuarioEmpresaValido() {
    return correoUsuario != null && !correoUsuario.isBlank() &&
        password != null && !password.isBlank() &&
        fechaNacimiento != null &&
        numeroTelefonico != null && !numeroTelefonico.isBlank() &&
        pais != null && !pais.isBlank() &&
        avatarPart != null;
  }

  public long getAvatarEmpresaSize() {
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

  public String getAvatarEmpresaType() {
    try {
      return avatarPart.getMediaType().toString();
    } catch (Exception e) {
      return "";
    }
  }

}
