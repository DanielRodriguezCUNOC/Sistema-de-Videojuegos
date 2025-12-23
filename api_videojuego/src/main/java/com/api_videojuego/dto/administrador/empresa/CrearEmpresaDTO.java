package com.api_videojuego.dto.administrador.empresa;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class CrearEmpresaDTO {

	@FormDataParam("nombreEmpresa")
	private String nombreEmpresa;
	@FormDataParam("descripcion")
	private String descripcion;
	@FormDataParam("estadoComentario")
	private String estadoComentario;
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

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstadoComentario() {
		return estadoComentario;
	}

	public void setEstadoComentario(String estadoComentario) {
		this.estadoComentario = estadoComentario;
	}

	public boolean esEmpresaValida() {
		return nombreEmpresa != null && !nombreEmpresa.isEmpty()
				&& descripcion != null && !descripcion.isEmpty()
				&& estadoComentario != null && !estadoComentario.isEmpty()
				&& correoUsuario != null && !correoUsuario.isEmpty()
				&& nombreCompleto != null && !nombreCompleto.isEmpty()
				&& password != null && !password.isEmpty() && fechaNacimiento != null
				&& numeroTelefonico != null && !numeroTelefonico.isEmpty()
				&& pais != null && !pais.isEmpty() && avatarPart != null;
	}
}
